package su.tease.project.feature.shop.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.shop.presentation.list.ShopsPage

class ShopFeature(
    store: Store<*>,
) : BaseFeatureComponent<ShopFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            ShopsPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
