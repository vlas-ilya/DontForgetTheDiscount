package su.tease.project.feature.shop.presentation.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.shop.domain.interactor.ShopInterceptor
import su.tease.project.feature.shop.presentation.R
import su.tease.project.feature.shop.presentation.save.action.SaveShopAction
import su.tease.project.feature.shop.presentation.save.action.SaveShopActionRequest
import su.tease.project.feature.shop.presentation.save.action.SaveShopActions
import timber.log.Timber

data class SaveShopActionImpl(
    private val interceptor: ShopInterceptor,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveShopAction {

    override fun run(request: SaveShopActionRequest) = suspendAction {
        dispatch(SaveShopActions.OnSave)
        try {
            val persisted = request.shop.takeIf { it.id.isNotBlank() }
                ?.let { interceptor.get(it.id) }
                ?: request.shop.copy(id = uuidProvider.uuid())

            interceptor.save(persisted)

            dispatch(SaveShopActions.OnSaveSuccess(request.target, persisted))
            dispatch(NotificationAction.ShowNotification(successNotification(request.shop.id.isBlank())))
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
