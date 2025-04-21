package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.BankPresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

fun BankPresetEntity.toDomain() = BankPreset(
    id = id,
    name = name,
    icon = IconPreset(iconUrl),
)

fun BankPreset.toEntity() = BankPresetEntity(
    id = id,
    name = name,
    iconUrl = icon.url,
)
