package su.tease.project.feature.preset.presentation.icon.select.action.impl

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction.Back
import su.tease.project.core.mvi.navigation.action.NavigationAction.ForwardToPage
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage
import su.tease.project.feature.preset.presentation.icon.save.action.ExternalSaveIconPresetActions.OnFinish
import su.tease.project.feature.preset.presentation.icon.save.action.ExternalSaveIconPresetActions.OnSaved
import su.tease.project.feature.preset.presentation.icon.select.action.ExternalSelectIconPresetAction.OnSelected
import su.tease.project.feature.preset.presentation.icon.select.action.SelectIconPresetAction

class SelectIconPresetActionImpl : SelectIconPresetAction {
    override fun run(payload: IconType) = interceptSuspendAction {
        dispatch(ForwardToPage(SaveIconPresetPage(payload)))
        interceptAction(OnFinish::class, OnSaved::class).onSuccess {
            dispatch(OnSelected(it.iconPreset)).join()
            dispatch(Back).join()
        }
    }
}
