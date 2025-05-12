package su.tease.project.feature.cacheback.presentation.list.action

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

interface LoadBankAccountListAction : MviUseCase<CacheBackDate?>

@Parcelize
sealed class LoadBankAccountListActions : PlainAction {

    data object OnLoad : LoadBankAccountListActions()
    data class OnDateSelect(val date: CacheBackDate) : LoadBankAccountListActions()
    data object OnFail : LoadBankAccountListActions()
    data class OnSuccess(
        val currentDate: CacheBackDate,
        val dates: PersistentList<CacheBackDate>,
        val list: PersistentList<BankAccount>,
    ) : LoadBankAccountListActions()
}
