package su.tease.project.feature.bank.integration.dependencies.data

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.bank.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.bank.integration.mapper.cashback.toDomain
import su.tease.project.feature.bank.integration.mapper.cashback.toExternal
import su.tease.project.feature.cashback.domain.interactor.CashBackListInteractor
import su.tease.project.feature.bank.domain.entity.CashBack as DomainCashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate as DomainCashBackDate

class CashBackIntegrationInteractorImpl(
    val cashBackListInteractor: CashBackListInteractor,
) : CashBackIntegrationInteractor {

    override suspend fun removeForOwnerId(bankAccountId: String) =
        cashBackListInteractor.removeByOwnerId(bankAccountId)

    override suspend fun save(cashBack: DomainCashBack, bankAccountId: String) =
        cashBackListInteractor.save(cashBack.toExternal(bankAccountId))

    override suspend fun listForOwner(bankAccountId: String) =
        cashBackListInteractor.listByOwnerId(bankAccountId).mapPersistent { it.toDomain() }

    override suspend fun listForOwners(bankAccountIds: List<String>) =
        cashBackListInteractor.listByOwnerIds(bankAccountIds)
            .groupBy { it.ownerId }
            .mapValues { (_, value) -> value.mapPersistent { it.toDomain() } }

    override suspend fun listForCashBackDate(date: DomainCashBackDate) =
        cashBackListInteractor.listByDate(date.toExternal())
            .groupBy { it.ownerId }
            .mapValues { (_, value) -> value.mapPersistent { it.toDomain() } }

    override suspend fun listDates(): PersistentList<DomainCashBackDate> =
        cashBackListInteractor.listDates().mapPersistent { it.toDomain() }
}
