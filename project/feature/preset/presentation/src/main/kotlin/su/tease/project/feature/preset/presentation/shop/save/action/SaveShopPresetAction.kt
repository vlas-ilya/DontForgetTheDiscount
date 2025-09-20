package su.tease.project.feature.preset.presentation.shop.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.ShopPreset

interface SaveShopPresetAction : MviUseCase<ShopPreset>

enum class SaveShopPresetError {
    DUPLICATE_ERROR,
    SOME_ERROR,
}

@Parcelize
sealed class SaveShopPresetActions : PlainAction {
    data object OnInit : SaveShopPresetActions()
    data object OnSave : SaveShopPresetActions()
    data class OnSaveSuccess(val cashBackOwnerPreset: ShopPreset) : SaveShopPresetActions()
    data class OnSaveFail(val error: SaveShopPresetError) : SaveShopPresetActions()
}
