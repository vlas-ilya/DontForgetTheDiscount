package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.store.Store

class MainPage1(
    store: Store<*>,
    private val target: Target,
) : BasePageComponent(store) {

    @Composable
    override fun Compose() {
        Column {

        }
    }

    @Parcelize
    data class Target(val text: String) : NavigationTarget.Page
}
