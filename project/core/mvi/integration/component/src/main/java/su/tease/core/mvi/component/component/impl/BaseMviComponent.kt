@file:Suppress("DEPRECATION")

package su.tease.core.mvi.component.component.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.Job
import su.tease.core.mvi.component.component.Component
import su.tease.project.core.mvi.api.action.Action
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import androidx.compose.runtime.State as ComposeState

abstract class BaseMviComponent(
    protected val store: Store<*>
) : Component() {

    @Composable
    protected inline fun <reified S : State, T> selectAsState(
        noinline selector: S.() -> T
    ): ComposeState<T?> =
        remember(store, selector) { store.select(selector) }.collectAsState(null)

    @Composable
    protected inline fun <reified S : State, T> selectAsState(
        selector: Selector<S, T>,
    ): ComposeState<T?> =
        remember(store, selector) { store.select(selector) }.collectAsState(null)

    @Composable
    protected inline fun <reified S : State> selectAsState(): ComposeState<S?> =
        remember(store, S::class) { store.select<S>() }.collectAsState(null)

    @Composable
    protected inline fun <reified S : State, T> selectAsState(
        noinline selector: S.() -> T,
        initial: T,
    ): ComposeState<T> =
        remember(store, selector) { store.select(selector) }.collectAsState(initial)

    @Composable
    protected inline fun <reified S : State, T> selectAsState(
        selector: Selector<S, T>,
        initial: T,
    ): ComposeState<T> =
        remember(store, selector) { store.select(selector) }.collectAsState(initial)

    @Composable
    protected inline fun <reified S : State, T> selectAsState(
        initial: T,
        selector: Selector<S, T>,
    ): ComposeState<T> =
        remember(store, selector) { store.select(selector) }.collectAsState(initial)

    @Composable
    protected inline fun <reified S : State> selectAsState(
        initial: S,
    ): ComposeState<S> =
        remember(store, S::class) { store.select<S>() }.collectAsState(initial)

    protected fun dispatch(action: Action): Job = store.dispatcher.dispatch(action)
}
