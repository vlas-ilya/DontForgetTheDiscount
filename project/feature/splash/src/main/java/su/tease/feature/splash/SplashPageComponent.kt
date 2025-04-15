package su.tease.feature.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import su.tease.core.component.component.impl.BasePageComponent
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.api.store.Dispatcher
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi_navigation.action.NavigationAction
import su.tease.project.feature.main.presentation.MainApp1

class SplashPageComponent<S : State>(
    store: Store<S>,
) : BasePageComponent(), Store<S> by store, Dispatcher by store.dispatcher {

    @Composable
    override fun Compose() {
        Text("Splash")

        LaunchedEffect(Unit) {
            delay(1000)
            dispatch(NavigationAction.ReplaceApp(MainApp1("From Splash")))
        }
    }
}