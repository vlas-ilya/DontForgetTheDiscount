package su.tease.project.feature.cashback.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

@Parcelize
data class SelectCashBackPresetPage(
    val ownerPreset: CashBackOwnerPreset,
) : NavigationTarget.Page {

    @Parcelize
    data class OnSelectAction(
        val selected: CashBackPreset?
    ) : PlainAction
}
