package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage
import su.tease.project.feature.preset.presentation.shop.select.action.ExternalSelectShopPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.shop.select.action.ExternalSelectShopPresetAction.OnSelected
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectShopPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectShopPresetActionActions

class SaveCashBackSelectShopPresetActionImpl : SaveCashBackSelectShopPresetAction {
    override fun run(payload: ShopPreset?) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectShopPresetPage(payload)))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            SaveCashBackSelectShopPresetActionActions.OnSelected(it.shopPreset)
        }
    }
}
