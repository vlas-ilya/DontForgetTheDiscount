package su.tease.project.feature.info.integration.module.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.bank.presentation.info.list.BankAccountsInfoPage
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
import su.tease.project.feature.preset.presentation.bank.info.list.ListBankPresetPage
import su.tease.project.feature.preset.presentation.cashback.info.list.ListInfoCashbackPresetPage
import su.tease.project.feature.preset.presentation.icon.info.list.ListIconPresetPage
import su.tease.project.feature.preset.presentation.icon.entity.IconType.BANK_ICON
import su.tease.project.feature.preset.presentation.icon.entity.IconType.CASH_BACK_ICON
import su.tease.project.feature.preset.presentation.icon.entity.IconType.SHOP_ICON
import su.tease.project.feature.preset.presentation.mcc.info.list.ListMccCodePresetPage
import su.tease.project.feature.preset.presentation.shop.info.list.ListShopPresetPage
import su.tease.project.feature.shop.presentation.info.list.ShopsInfoPage

class InfoListPageNavigationInterceptor : Interceptor {
    override fun intercept(action: PlainAction): List<PlainAction> {
        if (action !is NavigationAction.ForwardToPage) return listOf()
        val page = action.page as? Info ?: return listOf()
        return handleInfoNavigation(page)
    }

    private fun handleInfoNavigation(page: Info): List<PlainAction> = when (page) {
        Banks -> BankAccountsInfoPage()
        BankPresets -> ListBankPresetPage()
        Shops -> ShopsInfoPage()
        ShopPresets -> ListShopPresetPage()
        CashBackPresets -> ListInfoCashbackPresetPage()
        BankIconPresets -> ListIconPresetPage(BANK_ICON)
        CashBackIconPresets -> ListIconPresetPage(CASH_BACK_ICON)
        ShopIconPresets -> ListIconPresetPage(SHOP_ICON)
        MccCodePresets -> ListMccCodePresetPage()
    }
        .let { NavigationAction.ForwardToPage(it) }
        .let(::listOf)
}
