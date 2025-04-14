package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.action.NavigationAction

@Parcelize
data object MainApp1NavigationTarget : NavigationTarget.App

val mainApp1Navigation = AppNavigation(
    name = MainApp1NavigationTarget,
    initFeature = mainFeature1Navigation,
)

class MainApp1<S : State>(
    store: Store<S>,
) : BaseAppComponent<S>(
    store = store,
    target = MainApp1NavigationTarget,
) {
    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("MainApp1")
            Button(
                onClick = { dispatch(NavigationAction.SwitchApp(mainApp2Navigation)) }
            ) {
                Text(text = "Switch to App 2")
            }
            child()
        }
    }

    @Composable
    override fun NavigationBar(target: NavigationTarget.Feature) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Theme.sizes.navigationHeight)
        ) {
            Button(
                onClick = { dispatch(NavigationAction.SwitchFeature(mainFeature1Navigation)) }
            ) {
                Text(text = "Feature 1")
            }
            Button(
                onClick = { dispatch(NavigationAction.SwitchFeature(mainFeature2Navigation)) }
            ) {
                Text(text = "Feature 2")
            }
        }
    }
}
