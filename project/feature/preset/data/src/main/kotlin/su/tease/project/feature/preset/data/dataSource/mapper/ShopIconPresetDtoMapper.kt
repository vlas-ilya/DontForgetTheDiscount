package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerIconPresetEntity
import su.tease.project.feature.preset.data.dao.mapper.SHOP_ICON_PRESET_TYPE
import su.tease.project.feature.preset.data.dataSource.dto.ShopIconPresetDto
import su.tease.project.feature.preset.domain.entity.ShopIconPreset

fun ShopIconPresetDto.toDomain() = ShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ShopIconPresetDto.toEntity() = CashBackOwnerIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
    type = SHOP_ICON_PRESET_TYPE
)
