package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.action.ExternalSelectBankPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.bank.select.action.ExternalSelectBankPresetAction.OnSelected
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectBankPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectBankPresetActionActions

class SaveCashBackSelectBankPresetActionImpl : SaveCashBackSelectBankPresetAction {
    override fun run(payload: BankPreset?) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectBankPresetPage(payload)))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            SaveCashBackSelectBankPresetActionActions.OnSelected(it.bankPreset)
        }
    }
}
