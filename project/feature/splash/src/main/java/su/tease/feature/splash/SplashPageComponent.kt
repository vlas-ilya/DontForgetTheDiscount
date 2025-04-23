package su.tease.feature.splash

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.main.presentation.MainApp1

private const val SPLASH_DELAY = 1000L

class SplashPageComponent(store: Store<*>) : BasePageComponent(store) {
    @Composable
    override fun Compose() {
        Text("Splash")

        LaunchedEffect(Unit) {
            delay(SPLASH_DELAY)
            dispatch(NavigationAction.ReplaceApp(MainApp1("From Splash")))
        }
    }
}
