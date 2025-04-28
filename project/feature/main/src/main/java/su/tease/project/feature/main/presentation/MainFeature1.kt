package su.tease.project.feature.main.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store

class MainFeature1(store: Store<*>) : BaseFeatureComponent(store) {

    companion object {
        operator fun invoke(text: String) = feature(
            Target,
            MainPage1.Target(text),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
