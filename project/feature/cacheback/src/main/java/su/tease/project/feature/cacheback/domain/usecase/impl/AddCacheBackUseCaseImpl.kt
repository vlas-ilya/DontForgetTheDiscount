package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.BankId
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackAction
import su.tease.project.feature.cacheback.domain.usecase.AddCacheBackUseCase
import su.tease.project.feature.cacheback.presentation.AddFormState
import su.tease.project.feature.cacheback.presentation.AddFormStateValidationErrors

class AddCacheBackUseCaseImpl(
    private val repository: BankRepository,
    private val uuidProvider: UuidProvider,
) : AddCacheBackUseCase {

    override fun run(request: AddFormState) = suspendAction {
        val validationErrors = request.validate()
        if (validationErrors != null) {
            dispatch(AddCacheBackAction.OnValidationFail(validationErrors))
            return@suspendAction
        }
        dispatch(AddCacheBackAction.OnSave)
        try {
            val persisted = repository.get(request.bankId)
            val bank = persisted.copy(cacheBacks = persisted.cacheBacks.add(request.cacheBack))
            repository.save(bank)
            dispatch(AddCacheBackAction.OnSaveSuccess(request.cacheBack))
        } catch (_: RepositoryException) {
            dispatch(AddCacheBackAction.OnSaveFail)
        }
    }

    private fun AddFormState.validate(): AddFormStateValidationErrors? =
        AddFormStateValidationErrors(
            bank = bank == null,
            name = name == null,
            info = info == null,
            icon = icon == null,
        ).takeIf { it.bank || it.name || it.info || it.icon }

    private val AddFormState.bankId: BankId
        get() = bank?.id?.let(::BankId) ?: error("Incorrect validation")

    private val AddFormState.cacheBack: CacheBack
        get() = CacheBack(
            id = CacheBackId(uuidProvider.uuid()),
            name = name ?: error("Incorrect validation"),
            info = info ?: error("Incorrect validation"),
            icon = icon?.let { CacheBackIcon(it.url) } ?: error("Incorrect validation"),
            size = size ?: error("Incorrect validation"),
            codes = codes,
        )
}
