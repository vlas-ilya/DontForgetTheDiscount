package su.tease.project.feature.cacheback.presentation.preset.cacheback.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

interface SaveCacheBackPresetAction : MviUseCase<CacheBackPreset>

enum class SaveCacheBackPresetError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class SaveCacheBackPresetActions : PlainAction {
    data class OnInit(
        val bankPreset: BankPreset? = null,
        val cacheBackPreset: CacheBackPreset? = null,
    ) : SaveCacheBackPresetActions()

    data object OnSave : SaveCacheBackPresetActions()
    data class OnSaveSuccess(val cacheBankPreset: CacheBackPreset) : SaveCacheBackPresetActions()
    data class OnSaveFail(val error: SaveCacheBackPresetError) : SaveCacheBackPresetActions()
}
