package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

class MainPage4(store: Store<*>) : BasePageComponent(store) {

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override fun Compose() {
        Column {

        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
