@file:Suppress("DEPRECATION")

package su.tease.project.core.mvi.api.middleware

import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.store.Dispatcher

interface Middleware {
    fun couldHandle(action: Action): Boolean

    suspend fun handle(
        dispatcher: Dispatcher,
        action: Action,
    )
}
