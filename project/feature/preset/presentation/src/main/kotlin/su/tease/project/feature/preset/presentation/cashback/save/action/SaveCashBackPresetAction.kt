package su.tease.project.feature.preset.presentation.cashback.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset

interface SaveCashBackPresetAction : MviUseCase<CashBackPreset>


@Parcelize
sealed class ExternalSaveCashBackPresetActions : PlainAction {
    data class OnSaved(val cashBankPreset: CashBackPreset) : ExternalSaveCashBackPresetActions()
    data object OnFinish : ExternalSaveCashBackPresetActions()
}

enum class SaveCashBackPresetError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class SaveCashBackPresetActions : PlainAction {
    data class OnInit(
        val ownerPreset: CashBackOwnerPreset? = null,
        val preset: CashBackPreset? = null,
    ) : SaveCashBackPresetActions()

    data object OnSave : SaveCashBackPresetActions()
    data object OnSaveSuccess : SaveCashBackPresetActions()
    data class OnSaveFail(val error: SaveCashBackPresetError) : SaveCashBackPresetActions()
}