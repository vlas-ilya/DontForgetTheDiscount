package su.tease.project.core.mvi.impl.logger

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.state.State

interface StoreLogger {
    fun log(prevState: State, action: PlainAction, newState: State)
}
