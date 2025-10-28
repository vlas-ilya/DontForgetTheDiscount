@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.middleware.intercept

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.completeWith
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.utils.either.Either
import su.tease.project.core.utils.either.left
import su.tease.project.core.utils.either.right
import su.tease.project.core.utils.ext.applyAndGet
import kotlin.reflect.KClass

interface InterceptDispatcher : Dispatcher {
    suspend fun <T : PlainAction> interceptAction(clazz: KClass<T>): T

    suspend fun <Left : PlainAction, Right : PlainAction> interceptAction(
        left: KClass<Left>,
        right: KClass<Right>,
    ): Either<Left, Right>
}

fun interface InterceptSuspendAction : Action {
    suspend operator fun invoke(dispatcher: InterceptDispatcher)
}

inline fun interceptSuspendAction(
    crossinline block: suspend InterceptDispatcher.() -> Unit
) = InterceptSuspendAction {
    block(it)
}

class InterceptSuspendMiddleware : Middleware {

    private val mapMutex = Mutex()
    private val map =
        mutableMapOf<KClass<PlainAction>, MutableList<CompletableDeferred<PlainAction>>>()

    override fun couldHandle(action: Action) =
        action is InterceptSuspendAction || action is PlainAction && interceptedBySomebody(action)

    override suspend fun handle(dispatcher: Dispatcher, action: Action) {
        when {
            action is InterceptSuspendAction -> action(interceptDispatcher(dispatcher))
            action is PlainAction && interceptedBySomebody(action) -> notify(action)
            else -> dispatcher.dispatch(action)
        }
    }

    private fun interceptDispatcher(dispatcher: Dispatcher) = InterceptDispatcherImpl(
        dispatcher = dispatcher,
        map = map,
        mapMutex = mapMutex,
    )

    private fun interceptedBySomebody(action: PlainAction) = action::class in map

    private suspend fun notify(action: PlainAction) = mapMutex.withLock {
        map[action::class]?.forEach {
            it.completeWith(Result.success(action))
        }
        map.remove(action::class)
    }
}

private class InterceptDispatcherImpl(
    private val dispatcher: Dispatcher,
    private val map: MutableMap<KClass<PlainAction>, MutableList<CompletableDeferred<PlainAction>>>,
    private val mapMutex: Mutex,
) : InterceptDispatcher {

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : PlainAction> interceptAction(clazz: KClass<T>): T {
        val result = CompletableDeferred<T>()
        mapMutex.withLock {
            map.applyAndGet(clazz as KClass<PlainAction>, mutableListOf()) {
                it.apply { add(result as CompletableDeferred<PlainAction>) }
            }
        }
        return result.await()
    }

    override suspend fun <Left : PlainAction, Right : PlainAction> interceptAction(
        left: KClass<Left>,
        right: KClass<Right>,
    ): Either<Left, Right> {
        val result = CompletableDeferred<Either<Left, Right>>()
        val scope = CoroutineScope(Job())
        scope.launch {
            val left = interceptAction(left)
            result.complete(left.left())
            scope.cancel()
        }
        scope.launch {
            val right = interceptAction(right)
            result.complete(right.right())
            scope.cancel()
        }
        return result.await()
    }

    override fun dispatch(action: Action): Job = dispatcher.dispatch(action)
}
