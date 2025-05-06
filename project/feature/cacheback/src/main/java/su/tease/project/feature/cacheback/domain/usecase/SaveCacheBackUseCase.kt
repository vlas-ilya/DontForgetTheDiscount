package su.tease.project.feature.cacheback.domain.usecase

import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.presentation.reducer.SaveCacheBackState

data class SaveCacheBackRequest(
    val id: CacheBackId?,
    val bank: BankPreset,
    val name: CacheBackName,
    val info: CacheBackInfo,
    val icon: IconPreset,
    val size: CacheBackSize,
    val codes: PersistentList<CacheBackCode>,
    val date: CacheBackDate,
)

interface SaveCacheBackUseCase : MviUseCase<SaveCacheBackRequest>

@Parcelize
sealed class SaveCacheBackAction : PlainAction {
    data class OnInit(val saveFormState: SaveCacheBackState) : SaveCacheBackAction()
    data class OnSetDate(val date: CacheBackDate) : SaveCacheBackAction()
    data object OnSave : SaveCacheBackAction()
    data class OnSaveSuccess(val cacheBack: CacheBack) : SaveCacheBackAction()
    data object OnSaveFail : SaveCacheBackAction()
}
