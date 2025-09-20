package su.tease.project.feature.shop.integration.dependencies.presentation.navigation

import org.koin.core.context.loadKoinModules
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.shop.integration.mapper.cashback.toExternal
import su.tease.project.feature.shop.integration.module.integration.shopCashBackIntegrationModule
import su.tease.project.feature.shop.presentation.dependencies.navigation.SaveCashBackPage
import su.tease.project.feature.cashback.presentation.SaveCashBackFeature as ExternalSaveCashBackFeature

class SaveCashBackPageFromShopsPageInterceptor : Interceptor {

    override fun intercept(action: PlainAction): List<PlainAction> {
        if (action !is NavigationAction.ForwardToPage) return listOf()
        val page = action.page as? SaveCashBackPage ?: return listOf()
        loadKoinModules(shopCashBackIntegrationModule)
        return NavigationAction.ForwardToFeature(
            feature = ExternalSaveCashBackFeature(
                id = page.id,
                preset = page.cashBackPreset?.toExternal(),
                owner = page.shop?.toExternal(),
                size = page.size,
                date = page.date?.toExternal(),
            ),
        ).let(::listOf)
    }
}
