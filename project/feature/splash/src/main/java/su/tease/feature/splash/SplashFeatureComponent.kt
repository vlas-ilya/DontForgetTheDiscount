package su.tease.feature.splash

import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.MainFeatureNavigationTarget

class SplashFeatureComponent(
    store: Store<*>
) : BaseFeatureComponent<MainFeatureNavigationTarget>(store)
