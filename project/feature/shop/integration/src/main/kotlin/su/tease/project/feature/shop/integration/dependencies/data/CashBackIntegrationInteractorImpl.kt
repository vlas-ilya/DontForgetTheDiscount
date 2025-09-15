package su.tease.project.feature.shop.integration.dependencies.data

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.domain.interactor.CashBackListInteractor
import su.tease.project.feature.shop.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.shop.integration.mapper.toDomain
import su.tease.project.feature.shop.integration.mapper.toExternal
import su.tease.project.feature.shop.domain.entity.CashBack as DomainCashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate as DomainCashBackDate

class CashBackIntegrationInteractorImpl(
    val cashBackListInteractor: CashBackListInteractor,
) : CashBackIntegrationInteractor {

    override suspend fun removeForOwnerId(shopId: String) =
        cashBackListInteractor.removeByOwnerId(shopId)

    override suspend fun save(cashBack: DomainCashBack, shopId: String) =
        cashBackListInteractor.save(cashBack.toExternal(shopId))

    override suspend fun listForOwner(shopId: String) =
        cashBackListInteractor.listByOwnerId(shopId).mapPersistent { it.toDomain() }

    override suspend fun listForOwners(shopIds: List<String>) =
        cashBackListInteractor.listByOwnerIds(shopIds)
            .groupBy { it.ownerId }
            .mapValues { (_, value) -> value.mapPersistent { it.toDomain() } }

    override suspend fun listForCashBackDate(date: DomainCashBackDate) =
        cashBackListInteractor.listByDate(date.toExternal())
            .groupBy { it.ownerId }
            .mapValues { (_, value) -> value.mapPersistent { it.toDomain() } }

    override suspend fun listDates(): PersistentList<DomainCashBackDate> =
        cashBackListInteractor.listDates().mapPersistent { it.toDomain() }
}
