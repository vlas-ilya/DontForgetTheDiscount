package su.tease.project.core.mvi.api.selector

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store

@PublishedApi
internal inline fun <T, R> StateFlow<T>.mapState(
    crossinline transform: (value: T) -> R,
): StateFlow<R> = this
    .map { transform(it) }
    .stateIn(
        scope = CoroutineScope(Dispatchers.Unconfined),
        started = SharingStarted.Eagerly,
        initialValue = transform(this.value)
    )

inline fun <reified S : State, T> Store<*>.select(
    crossinline selector: S.() -> T
): StateFlow<T> =
    select<S>().mapState() { selector(it) }

inline fun <reified S : State, T> Store<*>.select(
    selector: Selector<S, T>,
): StateFlow<T> =
    select<S>().mapState() { selector.run { it.select() } }

inline fun <reified S : State> Store<*>.select(): StateFlow<S> =
    state.mapState() { (it.findState(S::class) as S) }
