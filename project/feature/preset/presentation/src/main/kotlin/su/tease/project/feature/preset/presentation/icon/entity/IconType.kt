package su.tease.project.feature.preset.presentation.icon.entity

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import su.tease.design.theme.api.Theme
import su.tease.project.feature.preset.domain.entity.IconPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor

enum class IconType(
    val getIcons: suspend (PresetInteractor) -> PersistentList<IconPreset>,
    val tint: @Composable () -> Color?,
    val clip: @Composable () -> Shape,
    val padding: @Composable () -> Dp,
    val size: @Composable () -> Dp,
) {
    BANK_ICON(
        getIcons = PresetInteractor::bankIconPresets,
        tint = { null },
        clip = { CircleShape },
        padding = { 0.dp },
        size = { Theme.Companion.sizes.size46 },
    ),
    SHOP_ICON(
        getIcons = PresetInteractor::shopIconPresets,
        tint = { null },
        clip = { CircleShape },
        padding = { 0.dp },
        size = { Theme.Companion.sizes.size46 },
    ),
    CASH_BACK_ICON(
        getIcons = PresetInteractor::cashBacksIconPresets,
        tint = { Theme.Companion.colors.iconTint },
        clip = { RoundedCornerShape(Theme.Companion.sizes.round4) },
        padding = { Theme.Companion.sizes.padding4 },
        size = { Theme.Companion.sizes.size46 },
    ),
}