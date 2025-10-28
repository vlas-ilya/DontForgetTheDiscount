package su.tease.project.feature.icon.presentation.action.impl

import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.domain.usecase.SaveIconUseCase
import su.tease.project.feature.icon.presentation.action.SelectIconAction
import su.tease.project.feature.icon.presentation.action.SelectIconActionRequest
import su.tease.project.feature.icon.presentation.action.SelectIconActions

class SelectIconActionImpl(
    private val saveIconUseCase: SaveIconUseCase,
) : SelectIconAction {

    override fun run(request: SelectIconActionRequest) = suspendAction {
        dispatch(SelectIconActions.OnStart)
        try {
            val filePath = saveIconUseCase.saveIcon(
                uri = request.uri,
                scale = request.scale,
                offset = request.offset,
                rotation = request.rotation,
            )
            dispatch(SelectIconActions.OnSave)
            dispatch(SelectIconActions.OnSelect(request.target, filePath))
        } catch (_: Throwable) {
            dispatch(SelectIconActions.OnSaveFail)
        }
    }
}
