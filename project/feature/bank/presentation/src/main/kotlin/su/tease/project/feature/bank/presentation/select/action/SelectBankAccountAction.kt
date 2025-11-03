package su.tease.project.feature.bank.presentation.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.bank.domain.entity.BankAccount

@Parcelize
sealed class ExternalSelectBankAccountActions : PlainAction {
    data class OnSelected(val bankAccount: BankAccount) : ExternalSelectBankAccountActions()
    data object OnFinish : ExternalSelectBankAccountActions()
}
