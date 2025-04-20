@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.middleware.logger

import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.middleware.Middleware
import su.tease.project.core.mvi.api.store.Dispatcher

class LoggerMiddleware(
    private val onLogAction: (PlainAction) -> Unit
) : Middleware {

    override fun couldHandle(action: Action) = action is PlainAction

    override suspend fun handle(dispatcher: Dispatcher, action: Action) {
        require(action is PlainAction)
        onLogAction(action)
    }
}
