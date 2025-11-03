package su.tease.project.feature.shop.integration.cashback.presentation.save.action

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerActionActions.OnSelected
import su.tease.project.feature.shop.integration.mapper.cashback.toExternal
import su.tease.project.feature.shop.presentation.select.SelectShopPage
import su.tease.project.feature.shop.presentation.select.action.ExternalSelectShopActions.OnFinish as OnExternalFinish
import su.tease.project.feature.shop.presentation.select.action.ExternalSelectShopActions.OnSelected as OnExternalSelected

class SaveCashBackSelectCashBackOwnerActionImpl : SaveCashBackSelectCashBackOwnerAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectShopPage()))
        interceptAction(OnExternalFinish::class, OnExternalSelected::class).onSuccess {
            dispatch(OnSelected(it.shop.toExternal())).join()
        }
    }
}
