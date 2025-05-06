package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository
import su.tease.project.feature.cacheback.domain.usecase.AddBankAction
import su.tease.project.feature.cacheback.domain.usecase.AddBankError
import su.tease.project.feature.cacheback.domain.usecase.AddBankUseCase

class AddBankUseCaseImpl(
    private val uuidProvider: UuidProvider,
    private val dictionaryRepository: DictionaryRepository,
) : AddBankUseCase {

    override fun run(request: BankPreset) = suspendAction {
        dispatch(AddBankAction.OnSave)
        val bankPreset = request.copy(
            id = uuidProvider.uuid(),
            name = request.name.trim()
        )
        try {
            val banks = dictionaryRepository.banks()
            if (banks.any { it.name == bankPreset.name }) {
                dispatch(AddBankAction.OnSaveFail(AddBankError.DUPLICATE_ERROR))
                return@suspendAction
            }
            dictionaryRepository.save(bankPreset)
            dispatch(AddBankAction.OnSaveSuccess(bankPreset))
        } catch (_: RepositoryException) {
            dispatch(AddBankAction.OnSaveFail(AddBankError.SOME_ERROR))
        }
    }
}
