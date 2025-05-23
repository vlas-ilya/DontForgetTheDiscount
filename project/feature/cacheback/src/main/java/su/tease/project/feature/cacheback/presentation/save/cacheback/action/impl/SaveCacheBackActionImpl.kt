package su.tease.project.feature.cacheback.presentation.save.cacheback.action.impl

import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.repository.BankAccountRepository
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackAction
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackActions
import su.tease.project.feature.cacheback.presentation.save.cacheback.action.SaveCacheBackRequest
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import timber.log.Timber

class SaveCacheBackActionImpl(
    private val bankAccountRepository: BankAccountRepository,
    private val presetInterceptor: PresetInterceptor,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveCacheBackAction {

    override fun run(request: SaveCacheBackRequest) = suspendAction {
        dispatch(SaveCacheBackActions.OnSave)
        try {
            val persistedBank = bankAccountRepository.get(request.bankAccount!!.id)
            val cacheBackPreset = presetInterceptor.cacheBackPreset(request.cacheBackPreset!!.id)
            val cacheBack = request.makeCacheBack(cacheBackPreset)
            val cacheBacks = persistedBank.cacheBacks.filter { it.id != cacheBack.id }
            if (cacheBacks.any { it.isDuplicateOf(cacheBack) }) {
                dispatch(SaveCacheBackActions.OnInit(request))
                dispatch(NotificationAction.ShowNotification(duplicateNotification()))
                return@suspendAction
            }
            val bank = persistedBank.copy(cacheBacks = (cacheBacks + cacheBack).toPersistentList())
            bankAccountRepository.save(bank)
            dispatch(SaveCacheBackActions.OnSaveSuccess(cacheBack))
            dispatch(NotificationAction.ShowNotification(successNotification(request.id == null)))
        } catch (e: RepositoryException) {
            Timber.e(e)
            dispatch(SaveCacheBackActions.OnSaveFail)
        }
    }

    private fun CacheBack.isDuplicateOf(
        cacheBack: CacheBack
    ) = cacheBackPreset.id == cacheBack.cacheBackPreset.id && date == cacheBack.date

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.page_save_bank_preset_notification_title,
                R.string.page_save_cache_back_notification_title,
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

    private fun SaveCacheBackRequest.makeCacheBack(cacheBackPreset: CacheBackPreset) = CacheBack(
        id = id ?: uuidProvider.uuid(),
        cacheBackPreset = cacheBackPreset,
        size = size!!,
        date = date!!,
    )
}
