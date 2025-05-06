package su.tease.project.feature.cacheback.domain.usecase.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.mapper.toCacheBackDate
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase

class LoadBankListUseCaseImpl(
    private val repository: BankRepository,
    private val dateProvider: DateProvider,
) : LoadBankListUseCase {

    override fun run(request: CacheBackDate?) = suspendAction {
        dispatch(LoadBankListAction.OnLoad)
        try {
            val date = request ?: dateProvider.current().toCacheBackDate()
            val list = repository.filterBy(date)
            val dates = repository.listDates().takeIf { it.isNotEmpty() } ?: persistentListOf(date)
            dispatch(LoadBankListAction.OnSuccess(date, dates, list.sortBanks()))
        } catch (_: RepositoryException) {
            dispatch(LoadBankListAction.OnFail)
        }
    }
}

private fun PersistentList<Bank>.sortBanks(): PersistentList<Bank> = this
    .map { it.copy(cacheBacks = it.cacheBacks.sortCacheBacks()) }
    .sortedBy { it.name.value }
    .toPersistentList()

private fun PersistentList<CacheBack>.sortCacheBacks(): PersistentList<CacheBack> = this
    .map { it.copy(codes = it.codes.sortCacheBackCodes()) }
    .sortedBy { it.name.value }
    .toPersistentList()

private fun PersistentList<CacheBackCode>.sortCacheBackCodes(): PersistentList<CacheBackCode> = this
    .sortedBy { it.code.value }
    .toPersistentList()
