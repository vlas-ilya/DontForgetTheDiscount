package su.tease.project.feature.shop.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop

interface ShopRepository {
    suspend fun save(value: Shop)
    suspend fun get(id: String): Shop
    suspend fun list(): PersistentList<Shop>
    suspend fun filterBy(date: CashBackDate): PersistentList<Shop>
    suspend fun listWithoutCashbacks(): PersistentList<Shop>
    suspend fun listDates(): PersistentList<CashBackDate>
    suspend fun delete(id: String): Boolean
}
