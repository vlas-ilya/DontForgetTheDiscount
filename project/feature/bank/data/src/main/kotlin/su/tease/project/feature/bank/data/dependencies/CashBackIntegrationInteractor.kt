package su.tease.project.feature.bank.data.dependencies

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.bank.domain.entity.CashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate

interface CashBackIntegrationInteractor {
    suspend fun removeForOwnerId(bankAccountId: String)
    suspend fun save(cashBack: CashBack, bankAccountId: String)
    suspend fun listForOwner(bankAccountId: String): PersistentList<CashBack>
    suspend fun listForOwners(bankAccountIds: List<String>): Map<String, PersistentList<CashBack>>
    suspend fun listForCashBackDate(date: CashBackDate): Map<String, PersistentList<CashBack>>
    suspend fun listDates(): PersistentList<CashBackDate>
}
