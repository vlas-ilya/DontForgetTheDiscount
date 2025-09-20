package su.tease.project.feature.cashback.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.bank.toDomain
import su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.shop.toDomain
import su.tease.project.feature.cashback.presentation.dependencies.navigation.SelectCashBackOwnerPage
import su.tease.project.feature.cashback.presentation.save.reducer.SaveCashBackReducer
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage as ExternalSelectBankAccountPage
import su.tease.project.feature.shop.presentation.select.SelectShopPage as ExternalSelectShopPage

class SelectCashBackOwnerPageFromSaveCashBackInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is ExternalSelectBankAccountPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        is ExternalSelectShopPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        else -> listOf()
    }

    private fun tryToHandleOnSelectAction(action: ExternalSelectBankAccountPage.OnSelectAction): List<PlainAction> {
        if (!action.target.current()) return listOf()
        return listOf(
            action,
            SelectCashBackOwnerPage.OnSelectAction(action.selected?.toDomain())
        )
    }

    private fun tryToHandleOnSelectAction(action: ExternalSelectShopPage.OnSelectAction): List<PlainAction> {
        if (!action.target.current()) return listOf()
        return listOf(
            action,
            SelectCashBackOwnerPage.OnSelectAction(action.selected?.toDomain())
        )
    }

    private fun String.current() = this == SaveCashBackReducer::class.java.name
}
