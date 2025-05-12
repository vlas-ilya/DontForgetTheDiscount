package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class MccCodePresetDto(
    val id: String,
    val code: String,
    val name: String,
    val info: String,
)
