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
@Suppress("MagicNumber")
internal inline fun <T, R> StateFlow<T>.mapState(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    crossinline transform: (value: T) -> R,
): StateFlow<R> = this
    .map { transform(it) }
    .stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = transform(this.value)
    )

inline fun <reified S : State, T> Store<*>.select(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    crossinline selector: S.() -> T
): StateFlow<T> =
    select<S>().mapState(scope) { selector(it) }

inline fun <reified S : State, T> Store<*>.select(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    selector: Selector<S, T>,
): StateFlow<T> =
    select<S>().mapState(scope) { selector.run { it.select() } }

inline fun <reified S : State> Store<*>.select(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
): StateFlow<S> =
    state.mapState(scope) { (it.findState(S::class) as S) }
