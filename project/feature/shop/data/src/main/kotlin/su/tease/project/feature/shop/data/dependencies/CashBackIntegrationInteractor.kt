package su.tease.project.feature.shop.data.dependencies

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate

interface CashBackIntegrationInteractor {
    suspend fun removeForOwnerId(shopId: String)
    suspend fun save(cashBack: CashBack, shopId: String)
    suspend fun listForOwner(shopId: String): PersistentList<CashBack>
    suspend fun listForOwners(shopIds: List<String>): Map<String, PersistentList<CashBack>>
    suspend fun listForCashBackDate(date: CashBackDate): Map<String, PersistentList<CashBack>>
    suspend fun listDates(): PersistentList<CashBackDate>
}
