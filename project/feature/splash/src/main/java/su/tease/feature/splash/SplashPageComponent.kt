package su.tease.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.main.presentation.MainApp

class SplashPageComponent(store: Store<*>) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) {
            replace(MainApp())
        }
    }
}
