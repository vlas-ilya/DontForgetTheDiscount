package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectMccCodeAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectMccCodeActions
import su.tease.project.feature.preset.presentation.mcc.select.SelectMccCodePresetPage
import su.tease.project.feature.preset.presentation.mcc.select.action.ExternalSelectMccCodeAction.OnFinish
import su.tease.project.feature.preset.presentation.mcc.select.action.ExternalSelectMccCodeAction.OnSelected

class SaveCashBackSelectMccCodeActionImpl : SaveCashBackSelectMccCodeAction {

    override fun run(payload: PersistentList<MccCodePreset>?) = interceptSuspendAction {
        dispatch(NavigationAction.ForwardToPage(SelectMccCodePresetPage(payload)))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SaveCashBackSelectMccCodeActions.OnSelected(it.mccCodes))
        }
    }
}
