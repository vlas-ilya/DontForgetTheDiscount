package su.tease.project.feature.preset.presentation.bank.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetError

class SaveBankPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
) : SaveBankPresetAction {

    override fun run(request: BankPreset) = suspendAction {
        dispatch(SaveBankPresetActions.OnSave)
        val bankPreset = request.copy(
            id = request.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid(),
            name = request.name.trim()
        )
        try {
            val banks = presetInteractor.bankPresets()
            if (banks.any { it.name == bankPreset.name && it.id != bankPreset.id }) {
                dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            presetInteractor.save(bankPreset)
            dispatch(SaveBankPresetActions.OnSaveSuccess(bankPreset))
        } catch (_: RepositoryException) {
            dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.SOME_ERROR))
        }
    }
}
