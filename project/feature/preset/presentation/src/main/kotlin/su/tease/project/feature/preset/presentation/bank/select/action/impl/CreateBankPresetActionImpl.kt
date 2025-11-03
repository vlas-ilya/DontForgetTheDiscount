package su.tease.project.feature.preset.presentation.bank.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.save.action.ExternalSaveBankPresetActions.OnFinish
import su.tease.project.feature.preset.presentation.bank.save.action.ExternalSaveBankPresetActions.OnSaved
import su.tease.project.feature.preset.presentation.bank.select.action.CreateBankPresetAction
import su.tease.project.feature.preset.presentation.bank.select.action.ExternalSelectBankPresetAction.OnSelected

class CreateBankPresetActionImpl : CreateBankPresetAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SaveBankPresetPage()))
        interceptAction(OnFinish::class, OnSaved::class).onSuccess {
            dispatch(OnSelected(it.bankPreset)).join()
            dispatch(NavigationAction.Back).join()
        }
    }
}
