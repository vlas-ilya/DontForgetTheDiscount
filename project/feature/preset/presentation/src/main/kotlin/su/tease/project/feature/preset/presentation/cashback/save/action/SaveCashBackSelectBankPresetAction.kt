package su.tease.project.feature.preset.presentation.cashback.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.BankPreset

interface SaveCashBackSelectBankPresetAction : MviUseCase<BankPreset?>

@Parcelize
sealed class SaveCashBackSelectBankPresetActionActions : PlainAction {
    data class OnSelected(val bankPreset: BankPreset) : SaveCashBackSelectBankPresetActionActions()
}
