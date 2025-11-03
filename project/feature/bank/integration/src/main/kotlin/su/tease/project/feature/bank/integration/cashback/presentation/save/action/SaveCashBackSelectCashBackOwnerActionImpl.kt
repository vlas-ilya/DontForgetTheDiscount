package su.tease.project.feature.bank.integration.cashback.presentation.save.action

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.bank.integration.mapper.cashback.toExternal
import su.tease.project.feature.bank.presentation.select.SelectBankAccountPage
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackSelectCashBackOwnerActionActions.OnSelected
import su.tease.project.feature.bank.presentation.select.action.ExternalSelectBankAccountActions.OnFinish as OnExternalFinish
import su.tease.project.feature.bank.presentation.select.action.ExternalSelectBankAccountActions.OnSelected as OnExternalSelected

class SaveCashBackSelectCashBackOwnerActionImpl : SaveCashBackSelectCashBackOwnerAction {
    override fun run() = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectBankAccountPage()))
        interceptAction(OnExternalFinish::class, OnExternalSelected::class).onSuccess {
            dispatch(OnSelected(it.bankAccount.toExternal())).join()
        }
    }
}
