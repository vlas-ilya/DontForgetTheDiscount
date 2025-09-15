package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.data.dataSource.dto.CashBackIconPresetDto
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset

fun CashBackIconPresetDto.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackIconPresetDto.toEntity() = CashBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
