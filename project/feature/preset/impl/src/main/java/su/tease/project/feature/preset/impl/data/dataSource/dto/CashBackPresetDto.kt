package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class CashBackPresetDto(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CashBackIconPresetDto,
    val mccCodes: List<MccCodePresetDto>
)
