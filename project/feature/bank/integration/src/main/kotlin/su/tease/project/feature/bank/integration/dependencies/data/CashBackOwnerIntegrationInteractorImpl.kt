package su.tease.project.feature.bank.integration.dependencies.data

import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.integration.mapper.cashback.toDomain
import su.tease.project.feature.bank.integration.mapper.cashback.toExternal
import su.tease.project.feature.cashback.data.dependencies.CashBackOwnerIntegrationInteractor
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId

class CashBackOwnerIntegrationInteractorImpl(
    private val bankAccountInterceptor: BankAccountInterceptor,
) : CashBackOwnerIntegrationInteractor {

    override suspend fun get(cashBackOwnerId: CashBackOwnerId) =
        bankAccountInterceptor.get(cashBackOwnerId).toExternal()

    override suspend fun save(cashBackOwner: CashBackOwner) =
        bankAccountInterceptor.save(cashBackOwner.toDomain())
}
