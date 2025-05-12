package su.tease.project.feature.main.presentation

import androidx.compose.runtime.Composable
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

class MainApp(store: Store<*>) : BaseAppComponent<MainApp.Target>(store) {

    @Composable
    override fun ComposeNavigationBar() {
        val feature = selectAsState(feature()).value

        NavigationBar(
            selected = feature,
            items = persistentListOf(
                NavigationBarItemData(
                    value = CacheBackFeature(),
                    name = "Cache Back",
                    image = painterResource(R.drawable.dollar),
                ),
            ),
            onSelect = { it.switchTo() },
            compare = FeatureNavigation::some,
        )
    }

    companion object {
        operator fun invoke() = app(
            Target,
            CacheBackFeature(),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.App
}
