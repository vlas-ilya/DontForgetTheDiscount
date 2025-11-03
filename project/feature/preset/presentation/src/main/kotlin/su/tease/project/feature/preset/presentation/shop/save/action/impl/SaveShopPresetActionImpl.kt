package su.tease.project.feature.preset.presentation.shop.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.shop.save.action.ExternalSaveShopPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetAction
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetActions
import su.tease.project.feature.preset.presentation.shop.save.action.SaveShopPresetError

class SaveShopPresetActionImpl(
    private val uuidProvider: UuidProvider,
    private val presetInteractor: PresetInteractor,
    private val resourceProvider: ResourceProvider,
) : SaveShopPresetAction {

    override fun run(payload: ShopPreset) = suspendAction {
        dispatch(SaveShopPresetActions.OnSave)
        val shopPreset = payload.copy(
            id = payload.id.takeIf { it.isNotBlank() } ?: uuidProvider.uuid(),
            name = payload.name.trim()
        )
        try {
            val shops = presetInteractor.shopPresets()
            if (shops.any { it.name == shopPreset.name && it.id != payload.id }) {
                dispatch(SaveShopPresetActions.OnSaveFail(SaveShopPresetError.DUPLICATE_ERROR))
                return@suspendAction
            }
            presetInteractor.save(shopPreset)
            dispatch(NotificationAction.ShowNotification(successNotification(payload.id.isBlank())))
            dispatch(SaveShopPresetActions.OnSaved).join()
            dispatch(ExternalSaveShopPresetActions.OnSaved(shopPreset)).join()
            dispatch(NavigationAction.Back).join()
        } catch (_: RepositoryException) {
            dispatch(SaveShopPresetActions.OnSaveFail(SaveShopPresetError.SOME_ERROR))
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.Presets_SaveShopPage_Notification_Add_Title,
                R.string.Presets_SaveShopPage_Notification_Save_Title,
            )
        ),
        closable = true,
    )
}
