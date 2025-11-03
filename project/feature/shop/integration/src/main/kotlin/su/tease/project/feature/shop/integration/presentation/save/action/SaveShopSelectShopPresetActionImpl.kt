package su.tease.project.feature.shop.integration.presentation.save.action

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage
import su.tease.project.feature.preset.presentation.shop.select.action.ExternalSelectShopPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.shop.select.action.ExternalSelectShopPresetAction.OnSelected
import su.tease.project.feature.shop.domain.entity.ShopPreset
import su.tease.project.feature.shop.integration.mapper.preset.toDomain
import su.tease.project.feature.shop.integration.mapper.preset.toExternal
import su.tease.project.feature.shop.presentation.save.action.SaveShopSelectShopPresetAction
import su.tease.project.feature.shop.presentation.save.action.SaveShopSelectShopPresetActions

class SaveShopSelectShopPresetActionImpl : SaveShopSelectShopPresetAction {
    override fun run(payload: ShopPreset?) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectShopPresetPage(payload?.toExternal())))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SaveShopSelectShopPresetActions.OnSelected(it.shopPreset.toDomain())).join()
        }
    }
}
