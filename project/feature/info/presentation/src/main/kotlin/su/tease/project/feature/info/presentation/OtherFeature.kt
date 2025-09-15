@file:Suppress("EmptyFunctionBlock")

package su.tease.project.feature.info.presentation

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store

class OtherFeature(
    store: Store<*>,
) : BaseFeatureComponent<OtherFeature.Target>(store) {

    companion object {
        operator fun invoke() = feature(
            Target,
            OtherPage.Target,
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}

class OtherPage(
    store: Store<*>,
) : BasePageComponent<OtherPage.Target>(store) {

    @Composable
    override fun invoke() {
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
