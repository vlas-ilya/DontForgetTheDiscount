package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.PageNavigation
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.action.NavigationAction

@Parcelize
data object MainPage2NavigationTarget : NavigationTarget.Page

val mainPage2Navigation = PageNavigation(
    name = MainPage2NavigationTarget
)

class MainPage2<S : State>(
    store: Store<S>,
) : BasePageComponent<S>(
    store = store,
    target = MainPage2NavigationTarget,
) {

    @Composable
    override fun Compose() {
        Column {
            Text("MainPage2")

            Button(
                onClick = { dispatch(NavigationAction.ForwardToPage(mainPage3Navigation)) }
            ) {
                Text("Forward To MainPage3")
            }
        }
    }
}
