package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

@Composable
fun IconSelect(
    icon: State<IconPreset?>,
    onSelect: () -> Unit,
) {
}
