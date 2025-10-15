package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetActions
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetError

class SaveCashBackPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
) : SaveCashBackPresetAction {

    override fun run(request: CashBackPreset) = suspendAction {
        dispatch(SaveCashBackPresetActions.OnSave)
        try {
            val cashBackPresets = presetInteractor.cashBackPresets(request.cashBackOwnerPreset.id)
            if (cashBackPresets.any { it.name == request.name && it.id != request.id }) {
                dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            val cashBackPreset = request.copy(
                id = request.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid()
            )
            presetInteractor.save(cashBackPreset)
            dispatch(SaveCashBackPresetActions.OnSaveSuccess(cashBackPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.SOME_ERROR))
        }
    }
}
