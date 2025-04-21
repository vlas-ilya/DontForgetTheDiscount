package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadBankListUseCase

class LoadBankListUseCaseImpl(
    private val repository: BankRepository,
) : LoadBankListUseCase {

    override fun run() = suspendAction {
        dispatch(LoadBankListAction.OnLoad)
        try {
            val list = repository.list()
            dispatch(LoadBankListAction.OnSuccess(list))
        } catch (_: RepositoryException) {
            dispatch(LoadBankListAction.OnFail)
        }
    }
}
