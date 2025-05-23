package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class MccCodePresetDto(
    val id: String,
    val code: String,
    val name: String,
    val info: String,
)
