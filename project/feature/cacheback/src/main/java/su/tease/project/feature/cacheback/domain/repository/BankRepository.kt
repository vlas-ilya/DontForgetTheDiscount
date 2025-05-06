package su.tease.project.feature.cacheback.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.repository.CrudRepository
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

interface BankRepository : CrudRepository<Bank> {
    suspend fun filterBy(date: CacheBackDate): PersistentList<Bank>
    suspend fun listDates(): PersistentList<CacheBackDate>
}
