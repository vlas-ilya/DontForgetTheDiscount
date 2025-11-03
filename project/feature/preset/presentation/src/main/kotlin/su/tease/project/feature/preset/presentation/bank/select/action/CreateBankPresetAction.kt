package su.tease.project.feature.preset.presentation.bank.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.preset.domain.entity.BankPreset

interface CreateBankPresetAction : MviNoParamUseCase

@Parcelize
sealed class CreateBankPresetActions : PlainAction {
    data class OnSelected(val bankPreset: BankPreset) : CreateBankPresetActions()
}
