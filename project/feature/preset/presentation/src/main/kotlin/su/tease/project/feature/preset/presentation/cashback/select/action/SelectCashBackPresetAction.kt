package su.tease.project.feature.preset.presentation.cashback.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.preset.domain.entity.CashBackPreset

@Parcelize
sealed class ExternalSelectCashBackPresetAction : PlainAction {
    data class OnSelected(val cashBackPreset: CashBackPreset) : ExternalSelectCashBackPresetAction()
    data object OnFinish : ExternalSelectCashBackPresetAction()
}
