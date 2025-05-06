package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

interface AddBankUseCase : MviUseCase<BankPreset>

enum class AddBankError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class AddBankAction : PlainAction {
    data object OnInit : AddBankAction()
    data object OnSave : AddBankAction()
    data class OnSaveSuccess(val bank: BankPreset) : AddBankAction()
    data class OnSaveFail(val error: AddBankError) : AddBankAction()
}
