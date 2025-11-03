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
    data class OnInit(val initShopPreset: ShopPreset? = null) : SaveShopPresetActions()
    data object OnSave : SaveShopPresetActions()
    data object OnSaved : SaveShopPresetActions()
    data class OnSaveFail(val error: SaveShopPresetError) : SaveShopPresetActions()
}

@Parcelize
sealed class ExternalSaveShopPresetActions : PlainAction {
    data class OnSaved(val shopPreset: ShopPreset) : ExternalSaveShopPresetActions()
    data object OnFinish : ExternalSaveShopPresetActions()
}
