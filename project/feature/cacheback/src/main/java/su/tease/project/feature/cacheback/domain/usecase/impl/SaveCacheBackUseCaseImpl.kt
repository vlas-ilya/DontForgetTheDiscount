package su.tease.project.feature.cacheback.domain.usecase.impl

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.ext.choose
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.BankIcon
import su.tease.project.feature.cacheback.domain.entity.BankId
import su.tease.project.feature.cacheback.domain.entity.BankName
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackAction
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackRequest
import su.tease.project.feature.cacheback.domain.usecase.SaveCacheBackUseCase
import su.tease.project.feature.notification.api.Notification
import su.tease.project.feature.notification.api.NotificationAction

class SaveCacheBackUseCaseImpl(
    private val repository: BankRepository,
    private val uuidProvider: UuidProvider,
    private val resourceProvider: ResourceProvider,
) : SaveCacheBackUseCase {

    override fun run(request: SaveCacheBackRequest) = suspendAction {
        dispatch(SaveCacheBackAction.OnSave)
        try {
            val persisted = repository.find(BankId(request.bank.id)) ?: request.bank.makeNew()
            val cacheBack = request.toDomain()
            val cacheBacks = persisted.cacheBacks.filter { it.id.value != cacheBack.id.value }
            val bank = persisted.copy(cacheBacks = (cacheBacks + cacheBack).toPersistentList())
            repository.save(bank)
            dispatch(SaveCacheBackAction.OnSaveSuccess(cacheBack))
            dispatch(NotificationAction.ShowNotification(successNotification(request.id == null)))
        } catch (e: RepositoryException) {
            println(e)
            dispatch(SaveCacheBackAction.OnSaveFail)
        }
    }

    private fun successNotification(isAdding: Boolean) = Notification(
        id = uuidProvider.uuid(),
        type = Notification.Type.SUCCESS,
        title = resourceProvider.string(
            isAdding.choose(
                R.string.page_add_cache_back_notification_title,
                R.string.page_save_cache_back_notification_title,
            )
        ),
        closable = true,
    )

    private fun BankPreset.makeNew() = Bank(
        id = BankId(id),
        name = BankName(name),
        icon = BankIcon(icon.url),
        cacheBacks = persistentListOf()
    )

    private fun SaveCacheBackRequest.toDomain() = CacheBack(
        id = id ?: CacheBackId(uuidProvider.uuid()),
        name = name,
        info = info,
        icon = CacheBackIcon(icon.url),
        size = size,
        codes = codes.mapPersistent { it.copy(id = CacheBackCodeId(uuidProvider.uuid())) },
        date = date,
    )
}
