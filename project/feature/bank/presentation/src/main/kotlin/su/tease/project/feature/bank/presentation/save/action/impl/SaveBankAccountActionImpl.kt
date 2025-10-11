package su.tease.project.feature.bank.presentation.save.action.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.interactor.BankAccountInterceptor
import su.tease.project.feature.bank.presentation.R
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountAction
import su.tease.project.feature.bank.presentation.save.action.SaveBankAccountActions
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
            val persisted = request.takeIf { it.id.isNotBlank() }?.let { interceptor.get(it.id) }
            val bankAccount = request.copy(
                id = persisted?.id ?: uuidProvider.uuid(),
                cashBacks = persisted?.cashBacks ?: request.cashBacks
            )
            interceptor.save(bankAccount)
            dispatch(SaveBankAccountActions.OnSaveSuccess(bankAccount))
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
                R.string.Bank_SaveBankAccountPage_Notification_Add_Title,
                R.string.Bank_SaveBankAccountPage_Notification_Save_Title,
            )
        ),
        closable = true,
    )
}
