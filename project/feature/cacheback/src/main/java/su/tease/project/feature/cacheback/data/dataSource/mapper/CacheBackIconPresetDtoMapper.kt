package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackIconPresetEntity
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.CacheBackIconPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset

fun CacheBackIconPresetDto.toDomain() = CacheBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CacheBackIconPresetDto.toEntity() = CacheBackIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
