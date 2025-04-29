package su.tease.project.feature.cacheback.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class CacheBackCodePresetDto(
    val id: String,
    val code: String,
)
