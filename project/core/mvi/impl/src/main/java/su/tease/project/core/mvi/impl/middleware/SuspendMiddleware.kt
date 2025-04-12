package su.tease.project.core.mvi.impl.middleware

import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.action.SuspendAction
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.store.Dispatcher

class SuspendMiddleware : Middleware {

    override fun couldHandle(action: Action) = action is SuspendAction

    override suspend fun handle(dispatcher: Dispatcher, action: Action) {
        require(action is SuspendAction)
        action.invoke(dispatcher)
    }
}
