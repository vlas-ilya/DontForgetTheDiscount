package su.tease.project.feature.cacheback.presentation.save.bank.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.presentation.save.bank.save.action.SaveBankAccountActions as Save
import su.tease.project.feature.cacheback.presentation.save.bank.select.SelectBankAccountPage as Select
import su.tease.project.feature.cacheback.presentation.save.bank.select.reducer.SelectBankAccountState as S

class SelectBankAccountReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is Select.OnSelectAction -> copy(savedBankAccount = null)
        is Save.OnSaveSuccess -> copy(savedBankAccount = action.bankAccount)
        else -> this
    }
}

@Parcelize
data class SelectBankAccountState(val savedBankAccount: BankAccount? = null) : State
