package su.tease.project.feature.cashback.domain.repository

import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId

interface CashBackOwnerRepository {
    suspend fun get(cashBackOwnerId: CashBackOwnerId): CashBackOwner
    suspend fun save(cashBackOwner: CashBackOwner)
}
