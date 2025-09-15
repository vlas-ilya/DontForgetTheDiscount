package su.tease.project.feature.cashback.domain.interactor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate

interface CashBackListInteractor {

    suspend fun listDates(): PersistentList<CashBackDate>

    suspend fun listByOwnerId(ownerId: CashBackOwnerId): PersistentList<CashBack>

    suspend fun listByOwnerIds(ownerIds: List<CashBackOwnerId>): PersistentList<CashBack>

    suspend fun listByDate(date: CashBackDate): PersistentList<CashBack>

    suspend fun save(cashBack: CashBack)

    suspend fun removeByOwnerId(ownerId: CashBackOwnerId)
}

typealias CashBackOwnerId = String
