package su.tease.project.feature.info.presentation.list.utils

import su.tease.project.feature.info.presentation.R
import su.tease.project.feature.info.presentation.dependencies.navigation.BankIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.BankPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.Banks
import su.tease.project.feature.info.presentation.dependencies.navigation.CashBackIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.CashBackPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.MccCodePresets
import su.tease.project.feature.info.presentation.dependencies.navigation.ShopIconPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.ShopPresets
import su.tease.project.feature.info.presentation.dependencies.navigation.Shops

val infoItems = listOf(
    InfoItem(
        id = "bank",
        name = R.string.Info_InfoListPage_Item_Banks_Title,
        navigation = Banks
    ),
    InfoItem(
        id = "bankPresets",
        name = R.string.Info_InfoListPage_Item_BankPresets_Title,
        navigation = BankPresets
    ),
    InfoItem(
        id = "bankIconPresets",
        name = R.string.Info_InfoListPage_Item_BankIconPresets_Title,
        navigation = BankIconPresets
    ),
    InfoItem(
        id = "shops",
        name = R.string.Info_InfoListPage_Item_Shops_Title,
        navigation = Shops
    ),
    InfoItem(
        id = "shopPresets",
        name = R.string.Info_InfoListPage_Item_ShopPresets_Title,
        navigation = ShopPresets
    ),
    InfoItem(
        id = "shopIconPresets",
        name = R.string.Info_InfoListPage_Item_ShopIconPresets_Title,
        navigation = ShopIconPresets
    ),
    InfoItem(
        id = "cashBackPresets",
        name = R.string.Info_InfoListPage_Item_CashBackPresets_Title,
        navigation = CashBackPresets
    ),
    InfoItem(
        id = "cashBackIconPresets",
        name = R.string.Info_InfoListPage_Item_CashBackIconPresets_Title,
        navigation = CashBackIconPresets
    ),
    InfoItem(
        id = "mccCodePresets",
        name = R.string.Info_InfoListPage_Item_MccCodePresets_Title,
        navigation = MccCodePresets
    ),
)
