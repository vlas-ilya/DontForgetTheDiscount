package su.tease.project.feature.cashback.data.repository

import su.tease.project.feature.cashback.data.dependencies.CashBackOwnerIntegrationInteractor
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId
import su.tease.project.feature.cashback.domain.repository.CashBackOwnerRepository

class CashBackOwnerRepositoryImpl(
    val cashBackOwnerIntegrationInteractor: CashBackOwnerIntegrationInteractor
) : CashBackOwnerRepository {

    override suspend fun get(cashBackOwnerId: CashBackOwnerId) =
        cashBackOwnerIntegrationInteractor.get(cashBackOwnerId)

    override suspend fun save(cashBackOwner: CashBackOwner) =
        cashBackOwnerIntegrationInteractor.save(cashBackOwner)
}
