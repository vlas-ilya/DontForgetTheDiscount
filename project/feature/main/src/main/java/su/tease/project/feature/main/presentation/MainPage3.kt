package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

class MainPage3(store: Store<*>) : BasePageComponent(store) {

    @Composable
    override fun Compose() {
        Column {

        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}
