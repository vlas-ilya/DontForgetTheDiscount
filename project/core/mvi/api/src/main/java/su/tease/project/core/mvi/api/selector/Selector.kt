package su.tease.project.core.mvi.api.selector

import su.tease.project.core.mvi.api.state.State

fun interface Selector<S: State, T> {
    fun S.select(): T
}
