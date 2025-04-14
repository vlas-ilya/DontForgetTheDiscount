package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.action.NavigationAction

@Parcelize
data object MainFeature1NavigationTarget : NavigationTarget.Feature

val mainFeature1Navigation = FeatureNavigation(
    name = MainFeature1NavigationTarget,
    initPage = mainPage1Navigation,
)

class MainFeature1<S : State>(
    store: Store<S>,
) : BaseFeatureComponent<S>(
    store = store,
    target = MainFeature1NavigationTarget,
) {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { dispatch(NavigationAction.Back)}
            ) {
                Text("Back")
            }
            Text("MainFeature1")
            child()
        }
    }
}
