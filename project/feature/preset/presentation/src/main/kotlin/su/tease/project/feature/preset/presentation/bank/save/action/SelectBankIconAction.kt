package su.tease.project.feature.preset.presentation.bank.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.BankIconPreset

interface SelectBankIconAction : MviUseCase<BankIconPreset?>

@Parcelize
sealed class SelectBankIconActions : PlainAction {
    data class OnSelected(val iconPreset: BankIconPreset) : SelectBankIconActions()
}
