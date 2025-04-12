package su.tease.project.core.mvi.impl.store.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.impl.state.CombinedState
import su.tease.project.core.mvi.api.store.Store
import kotlin.reflect.KClass

inline fun <reified S : State, T> Store<*>.select(
    crossinline selector: (S) -> T
): Flow<T> = state.map {
    val state = (it.findState(S::class) as? S) ?: error("No State")
    selector(state)
}

@PublishedApi
internal fun State.findState(clazz: KClass<out State>): State? =
    if (this::class == clazz) {
        this
    } else if (this is CombinedState<*, *>) {
        state1.findState(clazz) ?: state2.findState(clazz)
    } else {
        null
    }
