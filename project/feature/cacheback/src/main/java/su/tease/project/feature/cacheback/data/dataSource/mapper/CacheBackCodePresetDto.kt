package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dataSource.dto.CacheBackCodePresetDto
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeValue
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackCodePreset

fun CacheBackCodePresetDto.toDomain() = CacheBackCodePreset(
    id = CacheBackCodeId(id),
    code = CacheBackCodeValue(code),
    usageCount = 0,
)
