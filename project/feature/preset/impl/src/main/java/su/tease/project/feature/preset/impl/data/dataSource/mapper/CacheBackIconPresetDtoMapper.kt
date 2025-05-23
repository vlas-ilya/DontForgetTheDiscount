package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.CacheBackIconPresetDto

fun CacheBackIconPresetDto.toDomain() = CacheBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CacheBackIconPresetDto.toEntity() = CacheBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
