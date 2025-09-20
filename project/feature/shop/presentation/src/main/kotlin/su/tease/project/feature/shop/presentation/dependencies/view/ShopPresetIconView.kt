package su.tease.project.feature.shop.presentation.dependencies.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import su.tease.project.feature.shop.domain.entity.ShopIconPreset

@Immutable
interface ShopPresetIconView {
    @Composable
    fun ComposeComponent(
        iconPreset: ShopIconPreset,
        name: String,
        modifier: Modifier,
        clip: Shape,
    )
}

@Composable
fun ShopPresetIconView.Compose(
    iconPreset: ShopIconPreset,
    name: String,
    modifier: Modifier = Modifier,
    clip: Shape = CircleShape,
) = ComposeComponent(
    iconPreset = iconPreset,
    name = name,
    modifier = modifier,
    clip = clip,
)
