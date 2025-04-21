package su.tease.project.feature.cacheback.presentation.add.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

@Composable
fun BankSelect(
    bank: State<BankPreset?>,
    onSelect: () -> Unit,
) {
}
