package su.tease.project.feature.bank.presentation.info.save

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.FeatureConfig
import su.tease.core.mvi.component.component.impl.BaseFeatureComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.core.mvi.navigation.feature
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.icons.R
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage

class SaveBankAccountInfoFeature(
    store: Store<*>,
) : BaseFeatureComponent<SaveBankAccountInfoFeature.Target>(store) {

    @Composable
    override operator fun invoke(child: @Composable () -> Unit) {
        RootConfig { copy(isFullscreen = true) }
        AppConfig { copy(hasNavigationBar = false) }
        FeatureConfig {
            copy(
                action = FeatureConfig.AppAction(
                    icon = R.drawable.cross_small,
                    onClick = ::onClosePressed,
                )
            )
        }
        child()
    }

    private fun onClosePressed() = SaveBankAccountInfoFeature().finish()

    companion object {
        operator fun invoke(
            bankAccount: BankAccount? = null
        ) = feature(
            Target,
            SaveBankAccountPage(bankAccount),
        )
    }

    @Parcelize
    data object Target : NavigationTarget.Feature
}