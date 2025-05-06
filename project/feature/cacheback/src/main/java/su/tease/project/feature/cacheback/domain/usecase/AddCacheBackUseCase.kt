package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.presentation.reducer.AddCacheBackState

data class AddCacheBackRequest(
    val bank: BankPreset,
    val name: CacheBackName,
    val info: CacheBackInfo,
    val icon: IconPreset,
    val size: CacheBackSize,
    val codes: PersistentList<CacheBackCode>,
    val date: CacheBackDate,
)

interface AddCacheBackUseCase : MviUseCase<AddCacheBackRequest>

@Parcelize
sealed class AddCacheBackAction : PlainAction {
    data class OnInit(val addFormState: AddCacheBackState) : AddCacheBackAction()
    data class OnSetDate(val date: CacheBackDate) : AddCacheBackAction()
    data object OnSave : AddCacheBackAction()
    data class OnSaveSuccess(val cacheBack: CacheBack) : AddCacheBackAction()
    data object OnSaveFail : AddCacheBackAction()
}
