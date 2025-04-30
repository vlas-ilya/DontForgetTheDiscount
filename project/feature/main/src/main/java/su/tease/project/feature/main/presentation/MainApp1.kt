package su.tease.project.feature.main.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BaseAppComponent
import su.tease.core.mvi.navigation.FeatureNavigation
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.app
import su.tease.design.component.navigationbar.NavigationBar
import su.tease.design.component.navigationbar.data.NavigationBarItemData
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.feature
import su.tease.project.design.icons.R
import su.tease.project.feature.cacheback.presentation.CacheBackFeature

class MainApp1(store: Store<*>) : BaseAppComponent(store,) {

    @Composable
    override fun ComposeNavigationBar() {
        LaunchedEffect(Unit) { rootConfig { copy(isFullscreen = true) } }
        val feature = selectAsState(feature()).value ?: return

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
                NavigationBarItemData(
                    value = CacheBackFeature(),
                    name = "Cache Back",
                    image = painterResource(R.drawable.dollar),
                ),
            ),
            onSelect = { switch(it) },
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
