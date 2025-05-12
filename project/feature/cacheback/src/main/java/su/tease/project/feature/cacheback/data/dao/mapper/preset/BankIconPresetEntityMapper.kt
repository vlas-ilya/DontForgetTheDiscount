package su.tease.project.feature.cacheback.data.dao.mapper.preset

import su.tease.project.feature.cacheback.data.dao.entity.preset.BankIconPresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset

fun BankIconPresetEntity.toDomain() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankIconPreset.toEntity() = BankIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
