package su.tease.project.feature.bank.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.bank.domain.entity.BankPreset

interface SaveBankAccountSelectBankPresetAction : MviUseCase<BankPreset?>

@Parcelize
sealed class SaveBankAccountSelectBankPresetActions : PlainAction {
    data class OnSelected(val bankPreset: BankPreset) : SaveBankAccountSelectBankPresetActions()
}