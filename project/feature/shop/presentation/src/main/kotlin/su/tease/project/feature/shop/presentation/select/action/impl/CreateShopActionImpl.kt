package su.tease.project.feature.shop.presentation.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.shop.presentation.save.SaveShopPage
import su.tease.project.feature.shop.presentation.select.action.CreateShopAction
import su.tease.project.feature.shop.presentation.select.action.ExternalSelectShopActions.OnSelected
import su.tease.project.feature.shop.presentation.save.action.ExternalSaveShopAction.OnFinish as OnExternalFinish
import su.tease.project.feature.shop.presentation.save.action.ExternalSaveShopAction.OnSaved as OnExternalSaved

class CreateShopActionImpl : CreateShopAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SaveShopPage()))
        interceptAction(OnExternalFinish::class, OnExternalSaved::class).onSuccess {
            dispatch(OnSelected(it.shop)).join()
            dispatch(NavigationAction.Back).join()
        }
    }
}
