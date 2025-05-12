package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class BankIconPresetDto(
    val id: String,
    val iconUrl: String,
)
