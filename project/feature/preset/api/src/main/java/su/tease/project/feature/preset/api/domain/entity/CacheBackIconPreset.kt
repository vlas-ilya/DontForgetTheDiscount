package su.tease.project.feature.preset.api.domain.entity

import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CacheBackIconPreset(
    override val id: String,
    override val iconUrl: String,
) : IconPreset(id, iconUrl)
