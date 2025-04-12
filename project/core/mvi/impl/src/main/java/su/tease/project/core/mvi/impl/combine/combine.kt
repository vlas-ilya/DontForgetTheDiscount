package su.tease.project.core.mvi.impl.combine

import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State

internal fun <S1 : State, S2 : State> combine(
    reducer1: Reducer<S1>,
    reducer2: Reducer<S2>,
): Reducer<CombinedState<S1, S2>> = CombinedReducer(
    reducer1 = reducer1,
    reducer2 = reducer2,
)
