package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

interface LoadBankListUseCase : MviUseCase<CacheBackDate?>

@Parcelize
sealed class LoadBankListAction : PlainAction {

    data object OnLoad : LoadBankListAction()
    data class OnDateSelect(val date: CacheBackDate) : LoadBankListAction()
    data object OnFail : LoadBankListAction()
    data class OnSuccess(
        val date: CacheBackDate,
        val dates: PersistentList<CacheBackDate>,
        val list: PersistentList<Bank>,
    ) : LoadBankListAction()
}
