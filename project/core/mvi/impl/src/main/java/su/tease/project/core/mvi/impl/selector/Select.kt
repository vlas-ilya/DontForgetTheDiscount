package su.tease.project.core.mvi.impl.selector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

inline fun <reified S : State, T> Store<*>.select(crossinline selector: S.() -> T): Flow<T> =
    state.map {
        val state = (it.findState(S::class) as? S) ?: error("No State")
        selector(state)
    }

inline fun <reified S : State, T> Store<*>.select(selector: Selector<S, T>): Flow<T> =
    state.map {
        val state = (it.findState(S::class) as? S) ?: error("No State")
        selector.run { state.select() }
    }

inline fun <reified S : State> Store<*>.select(): Flow<S> =
    state.map {
        (it.findState(S::class) as? S) ?: error("No State")
    }
