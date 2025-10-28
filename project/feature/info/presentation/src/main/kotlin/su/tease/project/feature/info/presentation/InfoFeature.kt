@file:Suppress("EmptyFunctionBlock")

package su.tease.project.feature.info.presentation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.info.presentation.list.InfoListPage

class InfoFeature(
    store: Store<*>,
) : BaseFeatureComponent<InfoFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            InfoListPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
