package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dataSource.dto.CacheBackPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

fun CacheBackPresetDto.toDomain() = CacheBackPreset(
    id = id,
    name = name,
    iconDto = iconDto.toDomain(),
)
