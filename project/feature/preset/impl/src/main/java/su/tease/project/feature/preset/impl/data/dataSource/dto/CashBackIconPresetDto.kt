package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class CashBackIconPresetDto(
    val id: String,
    val iconUrl: String,
)
