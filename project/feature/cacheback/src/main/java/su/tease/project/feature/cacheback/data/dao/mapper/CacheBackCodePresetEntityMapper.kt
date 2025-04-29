package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodePresetEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeValue
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackCodePreset

fun CacheBackCodePresetEntity.toDomain() = CacheBackCodePreset(
    id = CacheBackCodeId(id),
    code = CacheBackCodeValue(code),
    usageCount = usageCount,
)

fun CacheBackCodePreset.toEntity() = CacheBackCodePresetEntity(
    id = id.value,
    code = code.value,
    usageCount = usageCount,
)
