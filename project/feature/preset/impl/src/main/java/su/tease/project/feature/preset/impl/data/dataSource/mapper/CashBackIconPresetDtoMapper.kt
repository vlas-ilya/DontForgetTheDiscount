package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.CashBackIconPresetDto

fun CashBackIconPresetDto.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackIconPresetDto.toEntity() = CashBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
