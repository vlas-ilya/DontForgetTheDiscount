package su.tease.project.feature.cacheback.presentation.preset.bank.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

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
