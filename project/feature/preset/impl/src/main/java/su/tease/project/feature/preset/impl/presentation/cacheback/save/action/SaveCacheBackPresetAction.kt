package su.tease.project.feature.preset.impl.presentation.cacheback.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

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
