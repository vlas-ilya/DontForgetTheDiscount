package su.tease.project.feature.bank.domain.interactor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate

interface BankAccountInterceptor {
    suspend fun save(bankAccount: BankAccount)
    suspend fun get(id: String): BankAccount
    suspend fun list(): PersistentList<BankAccount>
    suspend fun filterBy(date: CashBackDate): PersistentList<BankAccount>
    suspend fun listDates(): PersistentList<CashBackDate>
    suspend fun delete(id: String): Boolean
}
