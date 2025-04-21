package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.CacheBackPresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

fun CacheBackPresetEntity.toDomain() = CacheBackPreset(
    id = id,
    name = name,
    icon = IconPreset(iconUrl),
)

fun CacheBackPreset.toEntity() = CacheBackPresetEntity(
    id = id,
    name = name,
    iconUrl = icon.url,
)
