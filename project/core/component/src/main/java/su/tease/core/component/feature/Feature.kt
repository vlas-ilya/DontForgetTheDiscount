package su.tease.core.component.feature

import su.tease.project.core.mvi.api.store.Store

abstract class FeatureComponent(
    private val store: Store<*>
)