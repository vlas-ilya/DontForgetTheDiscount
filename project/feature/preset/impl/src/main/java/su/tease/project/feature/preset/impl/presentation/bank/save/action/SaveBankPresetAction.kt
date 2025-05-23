package su.tease.project.feature.preset.impl.presentation.bank.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.api.domain.entity.BankPreset

interface SaveBankPresetAction : MviUseCase<BankPreset>

enum class SaveBankPresetError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class SaveBankPresetActions : PlainAction {
    data object OnInit : SaveBankPresetActions()
    data object OnSave : SaveBankPresetActions()
    data class OnSaveSuccess(val bankPreset: BankPreset) : SaveBankPresetActions()
    data class OnSaveFail(val error: SaveBankPresetError) : SaveBankPresetActions()
}
