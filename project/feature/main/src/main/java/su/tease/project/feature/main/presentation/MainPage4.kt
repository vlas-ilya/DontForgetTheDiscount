package su.tease.project.feature.main.presentation

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

class MainPage4(store: Store<*>) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() = Unit

    @Parcelize
    data object Target : NavigationTarget.Page
}
