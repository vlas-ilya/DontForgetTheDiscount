package su.tease.project.feature.shop.integration.cashback.presentation.save.action

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackPresetActions
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.select.action.ExternalSelectCashBackPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.cashback.select.action.ExternalSelectCashBackPresetAction.OnSelected
import su.tease.project.feature.shop.integration.mapper.cashback.toExternal
import su.tease.project.feature.shop.integration.mapper.cashback.toPreset

class SaveCashBackSelectCashBackPresetActionImpl : SaveCashBackSelectCashBackPresetAction {
    override fun run(payload: CashBackOwnerPreset) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectCashBackPresetPage(payload.toPreset())))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SaveCashBackSelectCashBackPresetActions.OnSelected(it.cashBackPreset.toExternal())).join()
        }
    }
}
