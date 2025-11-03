package su.tease.project.feature.shop.presentation.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.save.action.ExternalSaveShopAction
import su.tease.project.feature.shop.presentation.save.action.SaveShopAction
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions
import timber.log.Timber

data class SaveShopActionImpl(
    private val interceptor: ShopInterceptor,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveShopAction {

    override fun run(payload: Shop) = suspendAction {
        dispatch(SaveShopActions.OnSave)
        try {
            val persisted = payload.takeIf { it.id.isNotBlank() }?.let { interceptor.get(it.id) }
            val shop = payload.copy(
                id = persisted?.id ?: uuidProvider.uuid(),
                cashBacks = persisted?.cashBacks ?: payload.cashBacks
            )
            interceptor.save(shop)
            dispatch(NotificationAction.ShowNotification(successNotification(payload.id.isBlank())))
            dispatch(SaveShopActions.OnSaveSuccess).join()
            dispatch(ExternalSaveShopAction.OnSaved(shop)).join()
            dispatch(NavigationAction.Back).join()
        } catch (e: RepositoryException) {
            Timber.e(e)
            dispatch(SaveShopActions.OnSaveFail)
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.Shop_SaveShopPage_Notification_Add_Title,
                R.string.Shop_SaveShopPage_Notification_Save_Title,
            )
        ),
        closable = true,
    )
}
