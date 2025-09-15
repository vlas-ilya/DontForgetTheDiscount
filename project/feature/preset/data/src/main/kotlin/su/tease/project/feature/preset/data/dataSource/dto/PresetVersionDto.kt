package su.tease.project.feature.preset.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class PresetVersionDto(
    val banks: Int,
    val bankIcons: Int,
    val shops: Int,
    val shopIcons: Int,
    val cashBackIcons: Int,
    val mccCodes: Int,
)
