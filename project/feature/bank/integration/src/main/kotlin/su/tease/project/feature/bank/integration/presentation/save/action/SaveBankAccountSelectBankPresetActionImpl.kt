package su.tease.project.feature.bank.integration.presentation.save.action

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.bank.domain.entity.BankPreset
import su.tease.project.feature.bank.integration.mapper.preset.toDomain
import su.tease.project.feature.bank.integration.mapper.preset.toExternal
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountSelectBankPresetAction
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountSelectBankPresetActions
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.action.ExternalSelectBankPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.bank.select.action.ExternalSelectBankPresetAction.OnSelected

class SaveBankAccountSelectBankPresetActionImpl : SaveBankAccountSelectBankPresetAction {
    override fun run(payload: BankPreset?) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectBankPresetPage(payload?.toExternal())))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SaveBankAccountSelectBankPresetActions.OnSelected(it.bankPreset.toDomain())).join()
        }
    }
}
