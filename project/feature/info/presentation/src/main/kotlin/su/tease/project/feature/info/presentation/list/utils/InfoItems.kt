package su.tease.project.feature.info.presentation.list.utils

import su.tease.project.feature.info.presentation.R
import su.tease.project.feature.info.presentation.navigation.NavigationTargets

fun infoItems(navigationTargets: NavigationTargets) = listOf(
    InfoItem(
        id = "bank",
        name = R.string.Info_InfoListPage_Item_Banks_Title,
        navigation = navigationTargets.banks,
    ),
    InfoItem(
        id = "bankPresets",
        name = R.string.Info_InfoListPage_Item_BankPresets_Title,
        navigation = navigationTargets.bankPresets,
    ),
    InfoItem(
        id = "bankIconPresets",
        name = R.string.Info_InfoListPage_Item_BankIconPresets_Title,
        navigation = navigationTargets.bankIconPresets,
    ),
    InfoItem(
        id = "shops",
        name = R.string.Info_InfoListPage_Item_Shops_Title,
        navigation = navigationTargets.shops,
    ),
    InfoItem(
        id = "shopPresets",
        name = R.string.Info_InfoListPage_Item_ShopPresets_Title,
        navigation = navigationTargets.shopPresets,
    ),
    InfoItem(
        id = "shopIconPresets",
        name = R.string.Info_InfoListPage_Item_ShopIconPresets_Title,
        navigation = navigationTargets.shopIconPresets,
    ),
    InfoItem(
        id = "cashBackPresets",
        name = R.string.Info_InfoListPage_Item_CashBackPresets_Title,
        navigation = navigationTargets.cashBackPresets,
    ),
    InfoItem(
        id = "cashBackIconPresets",
        name = R.string.Info_InfoListPage_Item_CashBackIconPresets_Title,
        navigation = navigationTargets.cashBackIconPresets,
    ),
    InfoItem(
        id = "mccCodePresets",
        name = R.string.Info_InfoListPage_Item_MccCodePresets_Title,
        navigation = navigationTargets.mccCodePresets,
    ),
)
