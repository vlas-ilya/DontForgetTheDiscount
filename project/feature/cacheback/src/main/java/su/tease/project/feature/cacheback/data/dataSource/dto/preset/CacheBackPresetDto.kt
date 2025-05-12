package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class CacheBackPresetDto(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CacheBackIconPresetDto,
    val mccCodes: List<MccCodePresetDto>
)
