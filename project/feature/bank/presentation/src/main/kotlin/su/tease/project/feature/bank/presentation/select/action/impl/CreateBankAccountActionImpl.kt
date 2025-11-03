package su.tease.project.feature.bank.presentation.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.bank.presentation.save.SaveBankAccountPage
import su.tease.project.feature.bank.presentation.select.action.CreateBankAccountAction
import su.tease.project.feature.bank.presentation.select.action.ExternalSelectBankAccountActions.OnSelected
import su.tease.project.feature.bank.presentation.save.action.ExternalSaveBankAccountAction.OnFinish as OnExternalFinish
import su.tease.project.feature.bank.presentation.save.action.ExternalSaveBankAccountAction.OnSaved as OnExternalSaved

class CreateBankAccountActionImpl : CreateBankAccountAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SaveBankAccountPage()))
        interceptAction(OnExternalFinish::class, OnExternalSaved::class).onSuccess {
            dispatch(OnSelected(it.bankAccount)).join()
            dispatch(NavigationAction.Back).join()
        }
    }
}
