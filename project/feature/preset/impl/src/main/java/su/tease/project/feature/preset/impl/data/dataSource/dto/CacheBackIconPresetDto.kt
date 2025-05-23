package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class CacheBackIconPresetDto(
    val id: String,
    val iconUrl: String,
)
