package su.tease.project.feature.bank.presentation.info.list.reducer

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.reducer.Reducer
import su.tease.project.core.mvi.api.state.LoadingStatus
import su.tease.project.core.mvi.api.state.LoadingStatus.Failed
import su.tease.project.core.mvi.api.state.LoadingStatus.Init
import su.tease.project.core.mvi.api.state.LoadingStatus.Loading
import su.tease.project.core.mvi.api.state.LoadingStatus.Success
import su.tease.project.core.mvi.api.state.State
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.presentation.info.list.action.LoadBankAccountsInfoActions

class BankAccountsInfoPageReducer : Reducer<S> {

    override val initState = S()

    override fun S.onAction(action: PlainAction) = when (action) {
        is LoadBankAccountsInfoActions -> onLoadList(action)
        else -> this
    }

    fun S.onLoadList(action: LoadBankAccountsInfoActions): S = when (action) {
        is LoadBankAccountsInfoActions.OnLoad -> copy(status = Loading)
        is LoadBankAccountsInfoActions.OnFail -> copy(status = Failed)
        is LoadBankAccountsInfoActions.OnSuccess -> copy(status = Success, list = action.list)
    }
}

@Parcelize
data class BankAccountsInfoPageState(
    val status: LoadingStatus = Init,
    val list: PersistentList<BankAccount> = persistentListOf(),
) : State

typealias S = BankAccountsInfoPageState
