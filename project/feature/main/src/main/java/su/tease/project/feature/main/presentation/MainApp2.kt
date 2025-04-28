package su.tease.project.feature.main.presentation

import androidx.compose.runtime.Composable
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
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.selector.feature
import su.tease.project.design.icons.R

class MainApp2(store: Store<*>) : BaseAppComponent(store) {

    @Composable
    override fun ComposeNavigationBar() {
        val feature = selectAsState(feature()).value ?: return

        NavigationBar(
            selected = feature,
            items = persistentListOf(
                NavigationBarItemData(
                    value = MainFeature2(),
                    name = "Feature 2",
                    image = painterResource(R.drawable.alarm_clock),
                ),
                NavigationBarItemData(
                    value = MainFeature3(),
                    name = "Feature 3",
                    image = painterResource(R.drawable.shopping_bag),
                ),
            ),
            onSelect = { switch(it) },
            compare = FeatureNavigation::some,
        )
    }

    companion object {
        operator fun invoke() = app(
            Target,
            MainFeature2(),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.App
}
