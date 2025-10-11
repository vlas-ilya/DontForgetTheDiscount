package su.tease.project.feature.shop.domain.interactor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop

interface ShopInterceptor {
    suspend fun save(shop: Shop)
    suspend fun get(id: String): Shop
    suspend fun list(): PersistentList<Shop>
    suspend fun filterBy(date: CashBackDate): PersistentList<Shop>
    suspend fun listWithoutCashbacks(): PersistentList<Shop>
    suspend fun listDates(): PersistentList<CashBackDate>
    suspend fun delete(id: String): Boolean
}
