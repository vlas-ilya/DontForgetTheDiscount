package su.tease.project.feature.info.integration.presentation.list.navigation


import su.tease.project.feature.bank.presentation.info.list.BankAccountsInfoPage
import su.tease.project.feature.info.presentation.navigation.NavigationTargets
import su.tease.project.feature.preset.presentation.bank.info.list.ListBankPresetPage
import su.tease.project.feature.preset.presentation.cashback.info.list.ListInfoCashbackPresetPage
import su.tease.project.feature.preset.presentation.icon.entity.IconType.BANK_ICON
import su.tease.project.feature.preset.presentation.icon.entity.IconType.CASH_BACK_ICON
import su.tease.project.feature.preset.presentation.icon.entity.IconType.SHOP_ICON
import su.tease.project.feature.preset.presentation.icon.info.list.ListIconPresetPage
import su.tease.project.feature.preset.presentation.mcc.info.list.ListMccCodePresetPage
import su.tease.project.feature.preset.presentation.shop.info.list.ListShopPresetPage
import su.tease.project.feature.shop.presentation.info.list.ShopsInfoPage

class NavigationTargetsImpl : NavigationTargets {
    override val banks = BankAccountsInfoPage()
    override val bankPresets = ListBankPresetPage()
    override val bankIconPresets = ShopsInfoPage()
    override val shops = ListShopPresetPage()
    override val shopPresets = ListInfoCashbackPresetPage()
    override val shopIconPresets = ListIconPresetPage(BANK_ICON)
    override val cashBackPresets = ListIconPresetPage(CASH_BACK_ICON)
    override val cashBackIconPresets = ListIconPresetPage(SHOP_ICON)
    override val mccCodePresets = ListMccCodePresetPage()
}