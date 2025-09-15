package su.tease.project.feature.preset.data.dao.mapper

import su.tease.project.feature.preset.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.domain.entity.MccCodePreset

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
