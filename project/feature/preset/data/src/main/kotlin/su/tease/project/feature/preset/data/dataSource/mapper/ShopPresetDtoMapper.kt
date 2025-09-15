package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.data.dao.mapper.SHOP_PRESET_TYPE
import su.tease.project.feature.preset.data.dataSource.dto.ShopPresetDto
import su.tease.project.feature.preset.domain.entity.ShopPreset

fun ShopPresetDto.toDomain() = ShopPreset(
    id = id,
    name = name,
    iconPreset = icon.toDomain(),
)

fun ShopPresetDto.toEntity() = CashBackOwnerPresetEntity(
    id = id,
    name = name,
    iconPresetId = icon.id,
    type = SHOP_PRESET_TYPE,
)
