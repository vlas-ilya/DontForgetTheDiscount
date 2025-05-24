package su.tease.project.feature.cashback.presentation.save.bank.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cashback.presentation.save.bank.save.action.SaveBankAccountAction
import su.tease.project.feature.cashback.presentation.save.bank.save.action.SaveBankAccountActions
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import timber.log.Timber

data class SaveBankAccountActionImpl(
    private val interceptor: BankAccountInterceptor,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveBankAccountAction {

    override fun run(request: BankAccount) = suspendAction {
        dispatch(SaveBankAccountActions.OnSave)
        try {
            val persisted = request.takeIf { it.id.isNotBlank() }
                ?.let { interceptor.get(it.id) }
                ?.let { request.copy(id = it.id) }
                ?: request.copy(id = uuidProvider.uuid())

            interceptor.save(persisted)

            dispatch(SaveBankAccountActions.OnSaveSuccess(persisted))
            dispatch(NotificationAction.ShowNotification(successNotification(request.id.isBlank())))
        } catch (e: RepositoryException) {
            Timber.e(e)
            dispatch(SaveBankAccountActions.OnSaveFail)
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.page_save_bank_account_notification_title_add,
                R.string.page_save_bank_account_notification_title_save,
            )
        ),
        closable = true,
    )
}
