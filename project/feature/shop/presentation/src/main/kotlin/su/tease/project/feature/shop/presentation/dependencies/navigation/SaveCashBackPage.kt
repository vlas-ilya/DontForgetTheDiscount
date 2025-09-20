package su.tease.project.feature.shop.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.CashBackPreset
import su.tease.project.feature.shop.domain.entity.Shop

@Parcelize
data class SaveCashBackPage(
    val id: String? = null,
    val shop: Shop? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
) : NavigationTarget.Page
