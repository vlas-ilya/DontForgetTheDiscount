package su.tease.project.feature.info.integration.module.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.info.presentation.dependencies.navigation.BankIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.BankPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.Banks
import su.tease.project.feature.info.presentation.dependencies.navigation.CashBackIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.CashBackPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.Info
import su.tease.project.feature.info.presentation.dependencies.navigation.MccCodePresets
import su.tease.project.feature.info.presentation.dependencies.navigation.ShopIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.ShopPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.Shops

class InfoListPageNavigationInterceptor : Interceptor {
    override fun intercept(action: PlainAction): List<PlainAction> {
        if (action !is NavigationAction.ForwardToPage) return listOf()
        val page = action.page as? Info ?: return listOf()
        return handleInfoNavigation(page)
    }

    private fun handleInfoNavigation(page: Info): List<PlainAction> = when (page) {
        BankIconPresets,
        BankPresets,
        Banks,
        CashBackIconPresets,
        CashBackPresets,
        MccCodePresets,
        ShopIconPresets,
        ShopPresets,
        Shops,
            -> emptyList()
    }
}
