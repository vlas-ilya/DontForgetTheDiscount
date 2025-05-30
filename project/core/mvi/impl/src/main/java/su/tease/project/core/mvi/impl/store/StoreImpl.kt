@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.impl.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.logger.StoreLogger

class StoreImpl<S : State>(
    private val reducer: Reducer<S>,
    private val middlewares: List<Middleware>,
    private val coroutineScope: CoroutineScope,
    private val logger: StoreLogger? = null,
    savedState: S? = null,
) : Store<S>, Dispatcher {
    private val stateMutex = Mutex()
    private val stateFlow = MutableStateFlow(savedState ?: reducer.initState)

    override val state = stateFlow.asStateFlow()

    override val dispatcher: Dispatcher = this

    override fun dispatch(action: Action) = coroutineScope.launch(Dispatchers.Default) {
        middlewares
            .filter { it.couldHandle(action) }
            .forEach { it.handle(dispatcher, action) }

        if (action is PlainAction) {
            stateMutex.withLock {
                val prevState = stateFlow.value
                val nextState = reducer.run { prevState.onAction(action) }
                logger?.log(prevState, action, nextState)
                stateFlow.emit(nextState)
            }
        }
    }
}
