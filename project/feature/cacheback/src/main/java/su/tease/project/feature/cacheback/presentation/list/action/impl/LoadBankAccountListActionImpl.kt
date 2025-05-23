package su.tease.project.feature.cacheback.presentation.list.action.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.repository.BankAccountRepository
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cacheback.presentation.list.action.LoadBankAccountListActions
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

class LoadBankAccountListActionImpl(
    private val repository: BankAccountRepository,
    private val dateProvider: DateProvider,
) : LoadBankAccountListAction {

    override fun run(request: CacheBackDate?) = suspendAction {
        dispatch(LoadBankAccountListActions.OnLoad)
        try {
            val date = request ?: dateProvider.current().toCacheBackDate()
            val list = repository.filterBy(date)
            val dates = repository.listDates().takeIf { it.isNotEmpty() } ?: persistentListOf(date)
            dispatch(LoadBankAccountListActions.OnSuccess(date, dates, list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadBankAccountListActions.OnFail)
        }
    }
}

private fun PersistentList<BankAccount>.sortBanks(): PersistentList<BankAccount> = this
    .map { it.copy(cacheBacks = it.cacheBacks.sortCacheBacks()) }
    .sortedBy { it.bankPreset.name }
    .toPersistentList()

private fun PersistentList<CacheBack>.sortCacheBacks(): PersistentList<CacheBack> = this
    .map { it.copy(cacheBackPreset = it.cacheBackPreset.withCodeSorted()) }
    .sortedBy { it.cacheBackPreset.name }
    .toPersistentList()

private fun CacheBackPreset.withCodeSorted() = this
    .copy(mccCodes = mccCodes.sortedBy { it.code }.toPersistentList())
