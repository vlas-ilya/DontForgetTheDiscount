package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.cacheback.domain.repository.CacheBackBankRepository
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListUseCase

class LoadCacheBackBankListUseCaseImpl(
    private val repository: CacheBackBankRepository,
) : LoadCacheBackBankListUseCase {

    override fun run() = suspendAction {
        dispatch(LoadCacheBackBankListAction.Loading)
        try {
            val list = repository.list()
            dispatch(LoadCacheBackBankListAction.Success(list))
        } catch (e: RepositoryException) {
            dispatch(LoadCacheBackBankListAction.Failed)
        }
    }
}
