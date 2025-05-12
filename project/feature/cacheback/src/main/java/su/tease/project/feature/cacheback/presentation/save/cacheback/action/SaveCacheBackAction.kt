package su.tease.project.feature.cacheback.presentation.save.cacheback.action

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

interface SaveCacheBackAction : MviUseCase<SaveCacheBackRequest>

@Parcelize
sealed class SaveCacheBackActions : PlainAction {
    data class OnInit(val request: SaveCacheBackRequest?) : SaveCacheBackActions()
    data class OnSetDate(val date: CacheBackDate) : SaveCacheBackActions()
    data object OnSave : SaveCacheBackActions()
    data class OnSaveSuccess(val cacheBack: CacheBack) : SaveCacheBackActions()
    data object OnSaveFail : SaveCacheBackActions()
}

@Parcelize
data class SaveCacheBackRequest(
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cacheBackPreset: CacheBackPreset? = null,
    val size: Int? = null,
    val date: CacheBackDate? = null,
) : Parcelable
