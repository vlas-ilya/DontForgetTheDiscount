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
data object MainFeature2NavigationTarget : NavigationTarget.Feature

val mainFeature2Navigation = FeatureNavigation(
    name = MainFeature2NavigationTarget,
    initPage = mainPage2Navigation,
)

class MainFeature2<S : State>(
    store: Store<S>,
) : BaseFeatureComponent<S>(
    store = store,
    target = MainFeature2NavigationTarget,
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
            Text("MainFeature2")
            child()
        }
    }
}
