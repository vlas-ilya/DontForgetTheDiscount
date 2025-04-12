package su.tease.project.core.mvi.impl.combine

import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State

internal class CombinedReducer<S1 : State, S2 : State>(
    private val reducer1: Reducer<S1>,
    private val reducer2: Reducer<S2>,
) : Reducer<CombinedState<S1, S2>> {

    override val initState: CombinedState<S1, S2> = CombinedState(
        reducer1.initState,
        reducer2.initState,
    )

    override fun CombinedState<S1, S2>.onAction(action: Action): CombinedState<S1, S2> {
        val newState1 = reducer1.run { state1.onAction(action) }
        val newState2 = reducer2.run { state2.onAction(action) }
        return when {
            state1 === newState1 && state2 === newState2 -> this
            state1 != newState1 -> copy(state1 = newState1)
            state2 != newState2 -> copy(state2 = newState2)
            else -> copy(state1 = newState1, state2 = newState2)
        }
    }
}
