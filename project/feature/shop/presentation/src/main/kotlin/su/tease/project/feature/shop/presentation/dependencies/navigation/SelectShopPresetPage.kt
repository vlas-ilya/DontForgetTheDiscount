package su.tease.project.feature.shop.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.shop.domain.entity.ShopPreset

@Parcelize
data class SelectShopPresetPage(
    val selected: ShopPreset?,
) : NavigationTarget.Page {

    @Parcelize
    data class OnSelectAction(val selected: ShopPreset?) : PlainAction
}
