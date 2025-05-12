package su.tease.project.feature.cacheback.data.dao.mapper.preset

import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackIconPresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset

fun CacheBackIconPresetEntity.toDomain() = CacheBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CacheBackIconPreset.toEntity() = CacheBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
