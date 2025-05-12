package su.tease.project.feature.cacheback.data.dataSource.dto.preset

import kotlinx.serialization.Serializable

@Serializable
data class BankPresetDto(
    val id: String,
    val name: String,
    val icon: BankIconPresetDto,
    val cacheBacks: List<CacheBackPresetDto>,
)
