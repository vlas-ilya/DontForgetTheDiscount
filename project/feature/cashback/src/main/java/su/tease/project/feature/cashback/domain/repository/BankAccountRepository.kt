package su.tease.project.feature.cashback.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.repository.CrudRepository
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate

interface BankAccountRepository : CrudRepository<BankAccount> {
    suspend fun filterBy(date: CashBackDate): PersistentList<BankAccount>
    suspend fun listDates(): PersistentList<CashBackDate>
}
