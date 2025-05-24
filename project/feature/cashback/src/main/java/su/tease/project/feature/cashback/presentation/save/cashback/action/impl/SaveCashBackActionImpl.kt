package su.tease.project.feature.cashback.presentation.save.cashback.action.impl

import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cashback.R
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.repository.BankAccountRepository
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackActions
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackRequest
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import timber.log.Timber

class SaveCashBackActionImpl(
    private val bankAccountRepository: BankAccountRepository,
    private val presetInterceptor: PresetInterceptor,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveCashBackAction {

    override fun run(request: SaveCashBackRequest) = suspendAction {
        dispatch(SaveCashBackActions.OnSave)
        try {
            val persistedBank = bankAccountRepository.get(request.bankAccount!!.id)
            val cashBackPreset = presetInterceptor.cashBackPreset(request.cashBackPreset!!.id)
            val cashBack = request.makeCashBack(cashBackPreset)
            val cashBacks = persistedBank.cashBacks.filter { it.id != cashBack.id }
            if (cashBacks.any { it.isDuplicateOf(cashBack) }) {
                dispatch(SaveCashBackActions.OnInit(request))
                dispatch(NotificationAction.ShowNotification(duplicateNotification()))
                return@suspendAction
            }
            val bank = persistedBank.copy(cashBacks = (cashBacks + cashBack).toPersistentList())
            bankAccountRepository.save(bank)
            dispatch(SaveCashBackActions.OnSaveSuccess(cashBack))
            dispatch(NotificationAction.ShowNotification(successNotification(request.id == null)))
        } catch (e: RepositoryException) {
            Timber.e(e)
            dispatch(SaveCashBackActions.OnSaveFail)
        }
    }

    private fun CashBack.isDuplicateOf(
        cashBack: CashBack
    ) = cashBackPreset.id == cashBack.cashBackPreset.id && date == cashBack.date

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.page_save_bank_preset_notification_title,
                R.string.page_save_cash_back_notification_title,
            )
        ),
        closable = true,
    )

    private fun duplicateNotification() = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.ERROR,
        title = resourceProvider.string(R.string.page_save_bank_preset_duplicate_notification_title),
        closable = true,
    )

    private fun SaveCashBackRequest.makeCashBack(cashBackPreset: CashBackPreset) = CashBack(
        id = id ?: uuidProvider.uuid(),
        cashBackPreset = cashBackPreset,
        size = size!!,
        date = date!!,
    )
}
