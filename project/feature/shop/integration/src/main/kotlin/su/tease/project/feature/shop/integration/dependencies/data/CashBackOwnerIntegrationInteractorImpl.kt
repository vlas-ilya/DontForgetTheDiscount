package su.tease.project.feature.shop.integration.dependencies.data

import su.tease.project.feature.cashback.data.dependencies.CashBackOwnerIntegrationInteractor
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.integration.mapper.toDomain
import su.tease.project.feature.shop.integration.mapper.toExternal

class CashBackOwnerIntegrationInteractorImpl(
    private val shopInterceptor: ShopInterceptor,
) : CashBackOwnerIntegrationInteractor {

    override suspend fun get(cashBackOwnerId: CashBackOwnerId) =
        shopInterceptor.get(cashBackOwnerId).toExternal()

    override suspend fun save(cashBackOwner: CashBackOwner) =
        shopInterceptor.save(cashBackOwner.toDomain())
}
