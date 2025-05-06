package su.tease.project.feature.cacheback.domain.usecase.impl

import kotlinx.collections.immutable.persistentListOf
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.BankIcon
import su.tease.project.feature.cacheback.domain.entity.BankId
import su.tease.project.feature.cacheback.domain.entity.BankName
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackRequest
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase

class AddCacheBackUseCaseImpl(
    private val repository: BankRepository,
    private val uuidProvider: UuidProvider,
) : AddCacheBackUseCase {

    override fun run(request: AddCacheBackRequest) = suspendAction {
        dispatch(AddCacheBackAction.OnSave)
        try {
            val persisted = repository.find(BankId(request.bank.id)) ?: request.bank.makeNew()
            val cacheBack = request.toDomain()
            val bank = persisted.copy(cacheBacks = persisted.cacheBacks.add(cacheBack))
            repository.save(bank)
            dispatch(AddCacheBackAction.OnSaveSuccess(cacheBack))
        } catch (e: RepositoryException) {
            println(e)
            dispatch(AddCacheBackAction.OnSaveFail)
        }
    }

    private fun BankPreset.makeNew() = Bank(
        id = BankId(id),
        name = BankName(name),
        icon = BankIcon(icon.url),
        cacheBacks = persistentListOf()
    )

    private fun AddCacheBackRequest.toDomain() = CacheBack(
        id = CacheBackId(uuidProvider.uuid()),
        name = name,
        info = info,
        icon = CacheBackIcon(icon.url),
        size = size,
        codes = codes,
        date = date,
    )
}
