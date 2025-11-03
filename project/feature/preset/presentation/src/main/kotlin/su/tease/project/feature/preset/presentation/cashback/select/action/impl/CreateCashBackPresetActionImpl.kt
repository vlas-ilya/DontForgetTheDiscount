package su.tease.project.feature.preset.presentation.cashback.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.presentation.cashback.save.SaveCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.select.action.CreateCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.select.action.ExternalSelectCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.ExternalSaveCashBackPresetActions.OnFinish as OnExternalFinish
import su.tease.project.feature.preset.presentation.cashback.save.action.ExternalSaveCashBackPresetActions.OnSaved as OnExternalSaved

class CreateCashBackPresetActionImpl : CreateCashBackPresetAction {
    override fun run(payload: CashBackOwnerPreset) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SaveCashBackPresetPage(payload)))
        interceptAction(OnExternalFinish::class, OnExternalSaved::class).onSuccess {
            dispatch(ExternalSelectCashBackPresetAction.OnSelected(it.cashBankPreset)).join()
            dispatch(NavigationAction.Back).join()
        }
    }
}