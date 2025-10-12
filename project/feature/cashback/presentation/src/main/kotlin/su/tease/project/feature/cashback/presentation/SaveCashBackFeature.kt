package su.tease.project.feature.cashback.presentation

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.presentation.save.SaveCashBackPage
import su.tease.project.design.icons.R as RIcons

class SaveCashBackFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveCashBackFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = RIcons.drawable.cross_small,
                    onClick = { finish() },
                )
            )
        }
        child()
    }

    companion object {
        operator fun invoke(
            id: String? = null,
            preset: CashBackPreset? = null,
            owner: CashBackOwner? = null,
            size: Int? = null,
            date: CashBackDate? = null,
        ) = feature(
            Target,
            SaveCashBackPage(
                id = id,
                preset = preset,
                owner = owner,
                size = size,
                date = date,
            ),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}
