package su.tease.project.feature.bank.presentation.select.reducer

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.utils.ext.transformIf
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountActions.OnSaveSuccess as Save
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage.OnInit as Init
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage.OnSelectAction as Select
import su.tease.project.feature.bank.presentation.select.reducer.SelectBankAccountState as S

class SelectBankAccountPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction): S = when (action) {
        is Init -> S()
        is Select -> copy(savedBankAccount = null)
        is Save -> transformIf(action.target.current()) { copy(savedBankAccount = action.bankAccount) }
        else -> this
    }

    private fun String.current() = this == SelectBankAccountPageReducer::class.java.name
}

@Parcelize
data class SelectBankAccountState(val savedBankAccount: BankAccount? = null) : State
