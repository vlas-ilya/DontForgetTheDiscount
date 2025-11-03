package su.tease.project.feature.shop.presentation.save.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviUseCase
import su.tease.project.feature.shop.domain.entity.ShopPreset

interface SaveShopSelectShopPresetAction : MviUseCase<ShopPreset?>

@Parcelize
sealed class SaveShopSelectShopPresetActions : PlainAction {
    data class OnSelected(val shopPreset: ShopPreset) : SaveShopSelectShopPresetActions()
}