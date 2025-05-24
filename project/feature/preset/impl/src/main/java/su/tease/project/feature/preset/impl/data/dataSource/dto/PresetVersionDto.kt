package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class PresetVersionDto(
    val banks: Int,
    val bankIcons: Int,
    val cashBackIcons: Int,
    val mccCodes: Int,
)
