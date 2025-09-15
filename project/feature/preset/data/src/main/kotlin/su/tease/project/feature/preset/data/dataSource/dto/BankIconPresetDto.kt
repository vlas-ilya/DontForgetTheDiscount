package su.tease.project.feature.preset.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class BankIconPresetDto(
    val id: String,
    val iconUrl: String,
)
