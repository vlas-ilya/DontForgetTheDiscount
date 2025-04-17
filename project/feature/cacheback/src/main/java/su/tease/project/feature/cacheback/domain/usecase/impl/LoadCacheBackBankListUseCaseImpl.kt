package su.tease.project.feature.cacheback.domain.usecase.impl

import su.tease.project.core.mvi.middleware.suspend.suspendAction
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListAction
import su.tease.project.feature.cacheback.domain.usecase.LoadCacheBackBankListUseCase

class LoadCacheBackBankListUseCaseImpl : LoadCacheBackBankListUseCase {

    override fun run() = suspendAction {
        dispatch(LoadCacheBackBankListAction.Loading)
    }
}
