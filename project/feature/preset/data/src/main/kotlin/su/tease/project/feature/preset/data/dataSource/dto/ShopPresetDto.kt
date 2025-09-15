package su.tease.project.feature.preset.data.dataSource.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShopPresetDto(
    val id: String,
    val name: String,
    val icon: ShopIconPresetDto,
    val cashBacks: List<CashBackPresetDto>,
)
