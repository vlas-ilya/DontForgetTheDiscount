package su.tease.project.feature.bank.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate

interface BankAccountRepository {
    suspend fun save(bankAccount: BankAccount)
    suspend fun get(id: String): BankAccount
    suspend fun list(): PersistentList<BankAccount>
    suspend fun listWithOutCashbacks(): PersistentList<BankAccount>
    suspend fun filterBy(date: CashBackDate): PersistentList<BankAccount>
    suspend fun listDates(): PersistentList<CashBackDate>
    suspend fun delete(id: String): Boolean
}
