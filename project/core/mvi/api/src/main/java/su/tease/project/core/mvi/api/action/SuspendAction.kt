package su.tease.project.core.mvi.api.action

import su.tease.project.core.mvi.api.store.Dispatcher

interface SuspendAction : Action {
    suspend operator fun invoke(dispatcher: Dispatcher)
}
