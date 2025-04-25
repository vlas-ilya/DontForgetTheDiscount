package su.tease.feature.splash

import androidx.compose.runtime.Composable
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.project.core.mvi.api.store.Store

class SplashFeatureComponent(store: Store<*>) : BaseFeatureComponent(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        child()
    }
}
