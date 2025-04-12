package su.tease.project.core.mvi.impl.combine

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.state.State
import kotlin.reflect.KClass

@Parcelize
internal data class CombinedState<S1 : State, S2 : State>(
    val state1: S1,
    val state2: S2,
) : State {
    override fun findState(clazz: KClass<out State>): State? =
        state1.findState(clazz) ?: state2.findState(clazz)
}
