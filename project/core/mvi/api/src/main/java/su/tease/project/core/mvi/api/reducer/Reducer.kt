package su.tease.project.core.mvi.api.reducer

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.state.State

interface Reducer<S : State> {

    val initState: S

    fun S.onAction(action: PlainAction): S
}
