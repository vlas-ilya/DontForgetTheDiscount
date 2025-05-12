package su.tease.project.feature.cacheback.domain.entity.preset

import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class BankIconPreset(
    override val id: String,
    override val iconUrl: String,
) : IconPreset(id, iconUrl)
