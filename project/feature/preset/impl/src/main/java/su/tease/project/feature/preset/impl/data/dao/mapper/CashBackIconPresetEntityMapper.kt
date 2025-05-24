package su.tease.project.feature.preset.impl.data.dao.mapper

import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackIconPresetEntity

fun CashBackIconPresetEntity.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackIconPreset.toEntity() = CashBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
