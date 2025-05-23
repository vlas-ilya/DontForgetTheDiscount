package su.tease.project.feature.preset.impl.data.dao.mapper

import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity

fun BankIconPresetEntity.toDomain() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankIconPreset.toEntity() = BankIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
