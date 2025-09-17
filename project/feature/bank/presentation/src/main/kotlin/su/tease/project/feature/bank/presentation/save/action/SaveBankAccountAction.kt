package su.tease.project.feature.bank.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.bank.domain.entity.BankAccount

interface SaveBankAccountAction : MviUseCase<BankAccount>

@Parcelize
sealed class SaveBankAccountActions : PlainAction {
    data class OnInit(val bankAccount: BankAccount? = null) : SaveBankAccountActions()
    data object OnSave : SaveBankAccountActions()
    data class OnSaveSuccess(val bankAccount: BankAccount) : SaveBankAccountActions()
    data object OnSaveFail : SaveBankAccountActions()
}
