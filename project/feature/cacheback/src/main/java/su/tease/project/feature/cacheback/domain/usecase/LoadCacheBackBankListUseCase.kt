package su.tease.project.feature.cacheback.domain.usecase

import android.os.Parcelable
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
    data class Failed(val error: LoadCacheBackBankListError) : LoadCacheBackBankListAction
}

@Parcelize
data class LoadCacheBackBankListError(val message: String) : Parcelable

@Parcelize
data class UserApprove(val approve: Boolean) : PlainAction
