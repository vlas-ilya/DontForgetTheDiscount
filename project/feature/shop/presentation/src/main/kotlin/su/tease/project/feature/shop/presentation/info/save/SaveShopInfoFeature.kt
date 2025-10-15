package su.tease.project.feature.shop.presentation.info.save

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.icons.R
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.presentation.save.SaveShopPage

class SaveShopInfoFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveShopInfoFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = R.drawable.cross_small,
                    onClick = { finish() },
                )
            )
        }
        child()
    }

    companion object {
        operator fun invoke(
            shop: Shop? = null
        ) = feature(
            Target,
            SaveShopPage<Unit>(shop),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}