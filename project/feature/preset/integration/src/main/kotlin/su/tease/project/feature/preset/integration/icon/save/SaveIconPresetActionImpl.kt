package su.tease.project.feature.preset.integration.icon.save

import su.tease.project.core.mvi.middleware.intercept.interceptSuspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction.Back
import su.tease.project.core.mvi.navigation.action.NavigationAction.ForwardToPage
import su.tease.project.core.utils.either.onSuccess
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.icon.presentation.SelectIconPage
import su.tease.project.feature.icon.presentation.action.ExternalSelectIconActions.OnFinish
import su.tease.project.feature.icon.presentation.action.ExternalSelectIconActions.OnSelected
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.IconPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.icon.entity.IconType
import su.tease.project.feature.preset.presentation.icon.save.action.ExternalSaveIconPresetActions.OnSaved
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetAction

class SaveIconPresetActionImpl(
    private val resourceProvider: ResourceProvider,
    private val presetInteractor: PresetInteractor,
    private val uuidProvider: UuidProvider,
) : SaveIconPresetAction {

    override fun run(payload: IconType) = interceptSuspendAction {
        dispatch(ForwardToPage(SelectIconPage()))
        interceptAction(OnFinish::class, OnSelected::class).onSuccess {
            try {
                val iconPreset = makeIconPreset(uuidProvider.uuid(), payload, it.filePath)
                save(iconPreset)
                dispatch(NotificationAction.ShowNotification(successNotification()))
                dispatch(OnSaved(iconPreset)).join()
                dispatch(Back).join()
            } catch (_: Throwable) {
                dispatch(NotificationAction.ShowNotification(failedNotification()))
            }
        }
    }

    private fun makeIconPreset(id: String, iconType: IconType, iconUrl: String) =
        when (iconType) {
            IconType.BANK_ICON -> BankIconPreset(id, iconUrl)
            IconType.CASH_BACK_ICON -> CashBackIconPreset(id, iconUrl)
            IconType.SHOP_ICON -> ShopIconPreset(id, iconUrl)
        }

    private suspend fun save(iconPreset: IconPreset) =
        when (iconPreset) {
            is BankIconPreset -> presetInteractor.save(iconPreset)
            is CashBackIconPreset -> presetInteractor.save(iconPreset)
            is ShopIconPreset -> presetInteractor.save(iconPreset)
            else -> Unit
        }

    private fun successNotification() = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(R.string.Presets_SaveIconPresetPage_Notification_Success_Title),
        closable = true,
    )

    private fun failedNotification() = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.ERROR,
        title = resourceProvider.string(R.string.Presets_SaveIconPresetPage_Notification_Failed_Title),
        closable = true,
    )
}
