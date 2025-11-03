package su.tease.project.feature.icon.presentation.action.impl

import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction.Back
import su.tease.project.feature.domain.usecase.SaveIconUseCase
import su.tease.project.feature.icon.presentation.action.ExternalSelectIconActions.OnSelected
import su.tease.project.feature.icon.presentation.action.SelectIconAction
import su.tease.project.feature.icon.presentation.action.SelectIconActionRequest
import su.tease.project.feature.icon.presentation.action.SelectIconActions.OnStart
import su.tease.project.feature.icon.presentation.action.SelectIconActions.OnStoreFail
import su.tease.project.feature.icon.presentation.action.SelectIconActions.OnStored

class SelectIconActionImpl(
    private val saveIconUseCase: SaveIconUseCase,
) : SelectIconAction {

    override fun run(payload: SelectIconActionRequest) = suspendAction {
        dispatch(OnStart)
        try {
            val filePath = saveIconUseCase.saveIcon(
                uri = payload.uri,
                scale = payload.scale,
                offset = payload.offset,
                rotation = payload.rotation,
            )
            dispatch(OnStored).join()
            dispatch(OnSelected(filePath)).join()
            dispatch(Back).join()
        } catch (_: Throwable) {
            dispatch(OnStoreFail)
        }
    }
}
