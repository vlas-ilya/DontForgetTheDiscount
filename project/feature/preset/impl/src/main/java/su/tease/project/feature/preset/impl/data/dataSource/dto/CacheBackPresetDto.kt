package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class CacheBackPresetDto(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CacheBackIconPresetDto,
    val mccCodes: List<MccCodePresetDto>
)
