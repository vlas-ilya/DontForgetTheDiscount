package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.component.utils.AppContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.action.NavigationAction

@Parcelize
data object MainPage4NavigationTarget : NavigationTarget.Page

val mainPage4Navigation = PageNavigation(
    name = MainPage4NavigationTarget
)

class MainPage4<S : State>(
    store: Store<S>,
) : BasePageComponent<S>(
    store = store,
    target = MainPage4NavigationTarget,
) {

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override fun Compose() {
        Column {
            Text("MainPage4")
            Button(
                onClick = { dispatch(NavigationAction.ForwardToPage(mainPage1Navigation)) }
            ) {
                Text("Forward To MainPage1")
            }
        }
    }
}
