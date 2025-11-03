package su.tease.project.feature.preset.presentation.shop.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.clean.doman.usecase.MviNoParamUseCase
import su.tease.project.feature.preset.domain.entity.ShopPreset

interface CreateShopPresetAction : MviNoParamUseCase

@Parcelize
sealed class CreateShopPresetActions : PlainAction {
    data class OnSelected(val bankPreset: ShopPreset) : CreateShopPresetActions()
}
