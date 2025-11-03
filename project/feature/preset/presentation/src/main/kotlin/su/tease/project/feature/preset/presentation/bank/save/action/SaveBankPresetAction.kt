package su.tease.project.feature.preset.presentation.bank.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.BankPreset

interface SaveBankPresetAction : MviUseCase<BankPreset>

enum class SaveBankPresetError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class SaveBankPresetActions : PlainAction {
    data class OnInit(val initBankPreset: BankPreset? = null) : SaveBankPresetActions()
    data object OnSave : SaveBankPresetActions()
    data object OnSaved : SaveBankPresetActions()
    data class OnSaveFail(val error: SaveBankPresetError) : SaveBankPresetActions()
}

@Parcelize
sealed class ExternalSaveBankPresetActions : PlainAction {
    data class OnSaved(val bankPreset: BankPreset) : ExternalSaveBankPresetActions()
    data object OnFinish : ExternalSaveBankPresetActions()
}