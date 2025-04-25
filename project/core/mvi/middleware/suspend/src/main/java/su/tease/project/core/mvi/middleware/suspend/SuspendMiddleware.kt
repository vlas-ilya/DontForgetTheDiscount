@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.middleware.suspend

import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.store.Dispatcher

fun interface SuspendAction : Action {
    suspend operator fun invoke(dispatcher: Dispatcher)
}

inline fun suspendAction(
    crossinline block: suspend Dispatcher.() -> Unit
) = SuspendAction {
    block(it)
}

class SuspendMiddleware : Middleware {

    override fun couldHandle(action: Action) = action is SuspendAction

    override suspend fun handle(
        dispatcher: Dispatcher,
        action: Action,
    ) {
        require(action is SuspendAction)
        action(dispatcher)
    }
}
