package su.tease.project.feature.preset.presentation.shop.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.presentation.shop.save.SaveShopPresetPage
import su.tease.project.feature.preset.presentation.shop.save.action.ExternalSaveShopPresetActions.OnFinish
import su.tease.project.feature.preset.presentation.shop.save.action.ExternalSaveShopPresetActions.OnSaved
import su.tease.project.feature.preset.presentation.shop.select.action.CreateShopPresetAction
import su.tease.project.feature.preset.presentation.shop.select.action.ExternalSelectShopPresetAction.OnSelected

class CreateShopPresetActionImpl : CreateShopPresetAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SaveShopPresetPage()))
        interceptAction(OnFinish::class, OnSaved::class).onSuccess {
            dispatch(OnSelected(it.shopPreset))
            dispatch(NavigationAction.Back).join()
        }
    }
}
