package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectIconAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackSelectIconActions
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnSelected

class SaveCashBackSelectIconActionImpl : SaveCashBackSelectIconAction {
    override fun run(payload: CashBackIconPreset?) = interceptSuspendAction {
        dispatch(
            NavigationAction.ForwardToPage(
                SelectIconPresetPage(
                    iconType = IconType.CASH_BACK_ICON,
                    pageTitle = R.string.Presets_SaveCashBackPresetPage_IconSelect_PageTitle,
                    selected = payload
                )
            )
        )
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SaveCashBackSelectIconActions.OnSelected(it.iconPreset as CashBackIconPreset))
        }
    }
}