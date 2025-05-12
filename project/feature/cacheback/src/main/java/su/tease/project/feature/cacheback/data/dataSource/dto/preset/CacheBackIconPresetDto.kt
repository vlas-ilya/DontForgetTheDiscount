package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class CacheBackIconPresetDto(
    val id: String,
    val iconUrl: String,
)
