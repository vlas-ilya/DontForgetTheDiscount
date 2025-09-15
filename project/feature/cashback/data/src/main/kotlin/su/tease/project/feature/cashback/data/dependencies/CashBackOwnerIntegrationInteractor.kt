package su.tease.project.feature.cashback.data.dependencies

import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId

interface CashBackOwnerIntegrationInteractor {
    suspend fun get(cashBackOwnerId: CashBackOwnerId): CashBackOwner
    suspend fun save(cashBackOwner: CashBackOwner)
}
