package su.tease.project.feature.preset.impl.data.dao.mapper

import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackIconPresetEntity

fun CacheBackIconPresetEntity.toDomain() = CacheBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CacheBackIconPreset.toEntity() = CacheBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
