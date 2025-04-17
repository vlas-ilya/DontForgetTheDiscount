package su.tease.project.core.mvi.api.selector

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

inline fun <reified S : State, T> Store<*>.select(crossinline selector: S.() -> T): Flow<T> = state
    .mapNotNull { (it.findState(S::class) as? S) }
    .distinctUntilChanged()
    .map { selector(it) }
    .distinctUntilChanged()
    .flowOn(Dispatchers.Default)

inline fun <reified S : State, T> Store<*>.select(selector: Selector<S, T>): Flow<T> = state
    .mapNotNull { (it.findState(S::class) as? S) }
    .distinctUntilChanged()
    .map { selector.run { it.select() } }
    .distinctUntilChanged()
    .flowOn(Dispatchers.Default)

inline fun <reified S : State> Store<*>.select(): Flow<S> = state
    .mapNotNull { (it.findState(S::class) as? S) }
    .distinctUntilChanged()
    .flowOn(Dispatchers.Default)
