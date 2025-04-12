package su.tease.project.core.mvi.api.store

import kotlinx.coroutines.flow.StateFlow
import su.tease.project.core.mvi.api.state.State

interface Store<S : State> {
    val dispatcher: Dispatcher
    val state: StateFlow<S>
}
