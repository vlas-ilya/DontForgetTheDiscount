package su.tease.project.feature.bank.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.bank.domain.entity.BankPreset

@Parcelize
data class SelectBankPresetPage(
    val selected: BankPreset?,
) : NavigationTarget.Page {

    @Parcelize
    data class OnSelectAction(val selected: BankPreset?) : PlainAction
}
