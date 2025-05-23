package su.tease.project.feature.preset.impl.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class BankPresetDto(
    val id: String,
    val name: String,
    val icon: BankIconPresetDto,
    val cacheBacks: List<CacheBackPresetDto>,
)
