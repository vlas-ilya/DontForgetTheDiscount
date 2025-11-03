package su.tease.project.feature.preset.presentation.shop.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.preset.domain.entity.ShopIconPreset

interface SelectShopIconAction : MviUseCase<ShopIconPreset?>

@Parcelize
sealed class SelectShopIconActions : PlainAction {
    data class OnSelected(val iconPreset: ShopIconPreset) : SelectShopIconActions()
}
