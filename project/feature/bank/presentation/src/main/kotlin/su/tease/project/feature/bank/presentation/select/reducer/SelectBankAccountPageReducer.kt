package su.tease.project.feature.bank.presentation.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountActions
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage
import su.tease.project.feature.bank.presentation.select.reducer.SelectBankAccountState as S

class SelectBankAccountPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is SelectBankAccountPage.OnSelectAction -> copy(savedBankAccount = null)
        is SaveBankAccountActions.OnSaveSuccess -> copy(savedBankAccount = action.bankAccount)
        else -> this
    }
}

@Parcelize
data class SelectBankAccountState(val savedBankAccount: BankAccount? = null) : State
