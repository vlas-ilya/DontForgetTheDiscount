package su.tease.project.feature.preset.presentation.bank.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.preset.domain.entity.BankPreset

@Parcelize
sealed class ExternalSelectBankPresetAction : PlainAction {
    data class OnSelected(val bankPreset: BankPreset) : ExternalSelectBankPresetAction()
    data object OnFinish : ExternalSelectBankPresetAction()
}
