package su.tease.project.feature.cacheback.data.dao.mapper.preset

import su.tease.project.feature.cacheback.data.dao.entity.preset.MccCodePresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

fun MccCodePresetEntity.toDomain() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun MccCodePreset.toEntity() = MccCodePresetEntity(
    id = id,
    code = code,
    name = name,
    info = info,
)
