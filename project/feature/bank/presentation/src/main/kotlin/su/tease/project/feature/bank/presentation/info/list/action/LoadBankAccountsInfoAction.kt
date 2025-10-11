package su.tease.project.feature.bank.presentation.info.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.bank.domain.entity.BankAccount

interface LoadBankAccountsInfoAction : MviNoParamUseCase

@Parcelize
sealed class LoadBankAccountsInfoActions : PlainAction {
    data object OnLoad : LoadBankAccountsInfoActions()
    data object OnFail : LoadBankAccountsInfoActions()
    data class OnSuccess(val list: PersistentList<BankAccount>) : LoadBankAccountsInfoActions()
}
