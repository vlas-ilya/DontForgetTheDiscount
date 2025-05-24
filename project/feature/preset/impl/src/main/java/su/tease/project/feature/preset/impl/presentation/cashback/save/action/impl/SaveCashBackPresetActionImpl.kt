package su.tease.project.feature.preset.impl.presentation.cashback.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetActions
import su.tease.project.feature.preset.impl.presentation.cashback.save.action.SaveCashBackPresetError

class SaveCashBackPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInterceptor: PresetInterceptor,
) : SaveCashBackPresetAction {

    override fun run(request: CashBackPreset) = suspendAction {
        dispatch(SaveCashBackPresetActions.OnSave)
        try {
            val cashBackPresets = presetInterceptor.cashBackPresets(request.bankPreset.id)
            if (cashBackPresets.any { it.name == request.name }) {
                dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            val cashBackPreset = request.copy(
                id = request.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid()
            )
            presetInterceptor.save(cashBackPreset)
            dispatch(SaveCashBackPresetActions.OnSaveSuccess(cashBackPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.SOME_ERROR))
        }
    }
}
