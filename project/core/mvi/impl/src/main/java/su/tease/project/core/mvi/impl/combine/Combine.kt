package su.tease.project.core.mvi.impl.combine

import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.halves

internal fun <S1 : State, S2 : State> combine(
    reducer1: Reducer<S1>,
    reducer2: Reducer<S2>,
): Reducer<CombinedState<S1, S2>> = CombinedReducer(
    reducer1 = reducer1,
    reducer2 = reducer2,
)

fun <S1 : State, S2 : State> combine(
    state1: S1,
    state2: S2,
): State = CombinedState(
    state1 = state1,
    state2 = state2,
)

fun combine(vararg reducer: Reducer<*>): Reducer<*> = combine(reducer.toList())

fun combine(reducers: List<Reducer<*>>): Reducer<*> {
    require(reducers.isNotEmpty())
    if (reducers.size == 1) return reducers[0]
    return reducers.halves()
        .let { combine(it.first) to combine(it.second) }
        .let { combine(it.first, it.second) }
}
