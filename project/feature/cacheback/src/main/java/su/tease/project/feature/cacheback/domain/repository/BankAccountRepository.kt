package su.tease.project.feature.cacheback.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.repository.CrudRepository
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

interface BankAccountRepository : CrudRepository<BankAccount> {
    suspend fun filterBy(date: CacheBackDate): PersistentList<BankAccount>
    suspend fun listDates(): PersistentList<CacheBackDate>
}
