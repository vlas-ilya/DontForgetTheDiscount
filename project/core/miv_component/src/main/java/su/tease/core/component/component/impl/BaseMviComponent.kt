package su.tease.core.component.component.impl

import su.tease.core.component.component.Component
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store

abstract class BaseMviComponent<S : State>(
    store: Store<S>
) : Component, Store<S> by store, Dispatcher by store.dispatcher
