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

class StoreImpl<S : State>(
    private val reducer: Reducer<S>,
    private val middlewares: List<Middleware>,
    private val coroutineScope: CoroutineScope,
    savedState: S? = null,
) : Store<S>, Dispatcher {
    private val stateMutex = Mutex()
    private val stateFlow = MutableStateFlow(savedState ?: reducer.initState)

    override val state = stateFlow.asStateFlow()

    override val dispatcher: Dispatcher = this

    override fun dispatch(action: Action) = coroutineScope.launch(Dispatchers.Default) {
        val middlewares = middlewares.filter { it.couldHandle(action) }
        if (middlewares.isNotEmpty()) {
            middlewares.forEach { it.handle(dispatcher, action) }
            return@launch
        }

        if (action !is PlainAction) return@launch

        stateMutex.withLock {
            val currentState = stateFlow.value
            val nextState = reducer.run { currentState.onAction(action) }
            stateFlow.emit(nextState)
        }
    }
}
