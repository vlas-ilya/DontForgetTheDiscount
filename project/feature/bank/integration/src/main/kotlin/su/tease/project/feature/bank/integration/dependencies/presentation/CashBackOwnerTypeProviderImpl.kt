package su.tease.project.feature.bank.integration.dependencies.presentation

import su.tease.project.feature.cashback.presentation.dependencies.CashBackOwnerTypeProvider

class CashBackOwnerTypeProviderImpl: CashBackOwnerTypeProvider {
    override fun get(): String = "bank"
}
