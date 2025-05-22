package su.tease.project.feature.cacheback.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.presentation.list.ListCacheBackPage

class OtherFeature(
    store: Store<*>,
) : BaseFeatureComponent<OtherFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            ListCacheBackPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
