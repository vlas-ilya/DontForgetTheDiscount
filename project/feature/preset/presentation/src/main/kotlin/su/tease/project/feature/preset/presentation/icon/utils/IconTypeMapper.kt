package su.tease.project.feature.preset.presentation.icon.utils

import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner

fun IconType.toIconOwner(): IconOwner = when (this) {
    IconType.CASH_BACK_ICON -> IconOwner.ForCashback
    IconType.BANK_ICON -> IconOwner.ForBank
    IconType.SHOP_ICON -> IconOwner.ForShop
}