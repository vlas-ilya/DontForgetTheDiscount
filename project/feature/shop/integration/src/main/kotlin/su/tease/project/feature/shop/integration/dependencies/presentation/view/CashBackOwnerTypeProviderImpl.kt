package su.tease.project.feature.shop.integration.dependencies.presentation.view

import su.tease.project.feature.cashback.presentation.dependencies.CashBackOwnerTypeProvider

class CashBackOwnerTypeProviderImpl: CashBackOwnerTypeProvider {
    override fun get(): String = "shop"
}