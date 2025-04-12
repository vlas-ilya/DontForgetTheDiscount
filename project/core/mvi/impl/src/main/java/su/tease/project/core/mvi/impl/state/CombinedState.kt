package su.tease.project.core.mvi.impl.state

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.state.State

@Parcelize
internal data class CombinedState<S1: State, S2: State>(
    val state1: S1,
    val state2: S2,
): State
