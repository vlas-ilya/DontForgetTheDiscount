package su.tease.project.feature.preset.presentation.shop.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnSelected
import su.tease.project.feature.preset.presentation.shop.save.action.SelectShopIconAction
import su.tease.project.feature.preset.presentation.shop.save.action.SelectShopIconActions

class SelectShopIconActionImpl : SelectShopIconAction {
    override fun run(payload: ShopIconPreset?) = interceptSuspendAction {
        dispatch(
            NavigationAction.ForwardToPage(
                SelectIconPresetPage(
                    iconType = IconType.SHOP_ICON,
                    pageTitle = R.string.Presets_SaveShopPage_IconSelect_PageTitle,
                    selected = payload
                )
            )
        )
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SelectShopIconActions.OnSelected(it.iconPreset as ShopIconPreset))
        }
    }
}