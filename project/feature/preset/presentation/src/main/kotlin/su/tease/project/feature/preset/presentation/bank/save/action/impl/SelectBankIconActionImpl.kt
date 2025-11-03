package su.tease.project.feature.preset.presentation.bank.save.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.save.action.SelectBankIconAction
import su.tease.project.feature.preset.presentation.bank.save.action.SelectBankIconActions
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.select.SelectIconPresetPage
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnFinish
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnSelected

class SelectBankIconActionImpl : SelectBankIconAction {
    override fun run(payload: BankIconPreset?) = interceptSuspendAction {
        dispatch(
            NavigationAction.ForwardToPage(
                SelectIconPresetPage(
                    iconType = IconType.BANK_ICON,
                    pageTitle = R.string.Presets_SaveBankPage_IconSelect_PageTitle,
                    selected = payload
                )
            )
        )
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            dispatch(SelectBankIconActions.OnSelected(it.iconPreset as BankIconPreset))
        }
    }
}