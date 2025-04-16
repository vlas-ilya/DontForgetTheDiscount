package su.tease.project.feature.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.app
import su.tease.design.component.navigationbar.NavigationBar
import su.tease.design.component.navigationbar.data.NavigationBarItemData
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.api.selector.select
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.mvi.navigation.selector.feature
import su.tease.project.design.icons.R

class MainApp1<S : State>(
    store: Store<S>,
) : BaseAppComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose(child: @Composable () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("MainApp1")
            Button(
                onClick = { dispatch(NavigationAction.SwitchApp(MainApp2())) },
            ) {
                Text(text = "Switch to App 2")
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
                    value = MainFeature1("From App 1"),
                    name = "Feature 1",
                    image = painterResource(R.drawable.antenna),
                ),
                NavigationBarItemData(
                    value = MainFeature2(),
                    name = "Feature 2",
                    image = painterResource(R.drawable.alarm_clock),
                ),
            ),
            onSelect = { dispatch(NavigationAction.SwitchFeature(it)) },
            compare = FeatureNavigation::some,
        )
    }

    companion object {
        operator fun invoke(text: String) = app(
            Target,
            MainFeature1(text),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.App
}
