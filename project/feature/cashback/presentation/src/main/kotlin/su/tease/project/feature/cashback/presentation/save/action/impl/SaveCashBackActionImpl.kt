@file:Suppress("TooGenericExceptionCaught")

package su.tease.project.feature.cashback.presentation.save.action.impl

import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cashback.domain.usecase.SaveCashBackUseCase
import su.tease.project.feature.cashback.presentation.R
import su.tease.project.feature.cashback.presentation.save.action.ExternalSaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.action.SaveCashBackActions
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import timber.log.Timber

class SaveCashBackActionImpl(
    private val saveCashBackUseCase: SaveCashBackUseCase,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveCashBackAction {

    override fun run(payload: SaveCashBackUseCase.Request) = suspendAction {
        dispatch(SaveCashBackActions.OnSave)
        try {
            val cashBack = saveCashBackUseCase.save(payload)
            dispatch(NotificationAction.ShowNotification(successNotification(payload.id == null)))
            dispatch(SaveCashBackActions.OnSaved).join()
            dispatch(ExternalSaveCashBackAction.OnSaved(cashBack)).join()
        } catch (_: SaveCashBackUseCase.DuplicateException) {
            dispatch(SaveCashBackActions.OnInit(payload))
            dispatch(NotificationAction.ShowNotification(duplicateNotification()))
        } catch (e: Throwable) {
            Timber.e(e)
            dispatch(SaveCashBackActions.OnSaveFail)
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.SaveCashBack_AddPage_SuccessNotification_Title,
                R.string.SaveCashBack_EditPage_SuccessNotification_Title,
            )
        ),
        closable = true,
    )

    private fun duplicateNotification() = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.ERROR,
        title = resourceProvider.string(R.string.SaveCashBack_AddPage_DuplicateNotification_Title),
        closable = true,
    )
}
