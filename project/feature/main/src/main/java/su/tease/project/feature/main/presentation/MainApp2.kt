package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.AppNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.component.navigation_bar.NavigationBar
import su.tease.design.component.navigation_bar.data.NavigationBarItemData
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.impl.selector.select
import su.tease.project.core.mvi_navigation.action.NavigationAction
import su.tease.project.core.mvi_navigation.selector.feature
import su.tease.project.design.icons.R

@Parcelize
data object MainApp2NavigationTarget : NavigationTarget.App

val mainApp2Navigation = AppNavigation(
    name = MainApp2NavigationTarget,
    initFeature = mainFeature2Navigation,
)

class MainApp2<S : State>(
    store: Store<S>,
) : BaseAppComponent<S>(
    store = store,
    target = MainApp2NavigationTarget,
) {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("MainApp2")
            Button(
                onClick = { dispatch(NavigationAction.SwitchApp(mainApp1Navigation)) }
            ) {
                Text(text = "Switch to App 1")
            }
            child()
        }
    }

    @Composable
    override fun ComposeNavigationBar(target: NavigationTarget.Feature) {
        val feature = select(feature()).collectAsState(null).value ?: return

        NavigationBar(
            selected = feature,
            items = persistentListOf(
                NavigationBarItemData(
                    value = mainFeature2Navigation,
                    name = "Feature 2",
                    image = painterResource(R.drawable.alarm_clock)
                ),

                NavigationBarItemData(
                    value = mainFeature3Navigation,
                    name = "Feature 3",
                    image = painterResource(R.drawable.shopping_bag)
                ),
            )
        ) {
            dispatch(NavigationAction.SwitchFeature(it))
        }
    }
}
