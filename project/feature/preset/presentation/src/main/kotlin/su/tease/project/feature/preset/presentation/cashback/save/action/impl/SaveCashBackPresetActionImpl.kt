package su.tease.project.feature.preset.presentation.cashback.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.cashback.save.action.ExternalSaveCashBackPresetActions
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetAction
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetActions
import su.tease.project.feature.preset.presentation.cashback.save.action.SaveCashBackPresetError

class SaveCashBackPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
    private val resourceProvider: ResourceProvider,
) : SaveCashBackPresetAction {

    override fun run(payload: CashBackPreset) = suspendAction {
        dispatch(SaveCashBackPresetActions.OnSave)
        try {
            val cashBackPresets = presetInteractor.cashBackPresets(payload.cashBackOwnerPreset.id)
            if (cashBackPresets.any { it.name == payload.name && it.id != payload.id }) {
                dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            val cashBackPreset = payload.copy(
                id = payload.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid()
            )
            dispatch(NotificationAction.ShowNotification(successNotification(payload.id.isBlank())))
            presetInteractor.save(cashBackPreset)
            dispatch(SaveCashBackPresetActions.OnSaveSuccess).join()
            dispatch(ExternalSaveCashBackPresetActions.OnSaved(cashBackPreset)).join()
            dispatch(NavigationAction.Back)
        } catch (_: RepositoryException) {
            dispatch(SaveCashBackPresetActions.OnSaveFail(SaveCashBackPresetError.SOME_ERROR))
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.Presets_SaveCashBackPresetPage_Notification_Add_Title,
                R.string.Presets_SaveCashBackPresetPage_Notification_Save_Title,
            )
        ),
        closable = true,
    )
}
