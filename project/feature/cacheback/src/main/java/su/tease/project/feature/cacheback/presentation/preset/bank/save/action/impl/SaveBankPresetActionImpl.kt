package su.tease.project.feature.cacheback.presentation.preset.bank.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.interceptor.PresetInterceptor
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.SaveBankPresetActions
import su.tease.project.feature.cacheback.presentation.preset.bank.save.action.SaveBankPresetError

class SaveBankPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInterceptor: PresetInterceptor,
) : SaveBankPresetAction {

    override fun run(request: BankPreset) = suspendAction {
        dispatch(SaveBankPresetActions.OnSave)
        val bankPreset = request.copy(
            id = uuidProvider.uuid(),
            name = request.name.trim()
        )
        try {
            val banks = presetInterceptor.bankPresets()
            if (banks.any { it.name == bankPreset.name }) {
                dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            presetInterceptor.save(bankPreset)
            dispatch(SaveBankPresetActions.OnSaveSuccess(bankPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.SOME_ERROR))
        }
    }
}
