package su.tease.project.feature.preset.presentation.shop.select.action

import kotlinx.parcelize.Parcelize
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.preset.domain.entity.ShopPreset

@Parcelize
sealed class ExternalSelectShopPresetAction : PlainAction {
    data class OnSelected(val shopPreset: ShopPreset) : ExternalSelectShopPresetAction()
    data object OnFinish : ExternalSelectShopPresetAction()
}
