package su.tease.project.feature.preset.data.dao.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset

fun CashBackIconPresetEntity.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackIconPreset.toEntity() = CashBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
