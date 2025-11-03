package su.tease.project.feature.preset.presentation.bank.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.save.action.ExternalSaveBankPresetActions
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetAction
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetActions
import su.tease.project.feature.preset.presentation.bank.save.action.SaveBankPresetError

class SaveBankPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
    private val resourceProvider: ResourceProvider,
) : SaveBankPresetAction {

    override fun run(payload: BankPreset) = suspendAction {
        dispatch(SaveBankPresetActions.OnSave)
        val bankPreset = payload.copy(
            id = payload.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid(),
            name = payload.name.trim()
        )
        try {
            val banks = presetInteractor.bankPresets()
            if (banks.any { it.name == bankPreset.name && it.id != bankPreset.id }) {
                dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            presetInteractor.save(bankPreset)
            dispatch(NotificationAction.ShowNotification(successNotification(payload.id.isBlank())))
            dispatch(SaveBankPresetActions.OnSaved).join()
            dispatch(ExternalSaveBankPresetActions.OnSaved(bankPreset)).join()
            dispatch(NavigationAction.Back).join()
        } catch (_: RepositoryException) {
            dispatch(SaveBankPresetActions.OnSaveFail(SaveBankPresetError.SOME_ERROR))
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.Presets_SaveBankPage_Notification_Add_Title,
                R.string.Presets_SaveBankPage_Notification_Save_Title,
            )
        ),
        closable = true,
    )
}
