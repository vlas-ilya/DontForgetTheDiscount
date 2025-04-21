package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank

interface LoadCacheBackBankListUseCase : MviNoParamUseCase

sealed interface LoadCacheBackBankListAction : PlainAction {

    @Parcelize
    data object Loading : LoadCacheBackBankListAction

    @Parcelize
    data class Success(val list: PersistentList<CacheBackBank>) : LoadCacheBackBankListAction

    @Parcelize
    data object Failed : LoadCacheBackBankListAction
}
