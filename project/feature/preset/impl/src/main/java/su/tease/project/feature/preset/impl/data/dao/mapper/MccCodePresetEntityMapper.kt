package su.tease.project.feature.preset.impl.data.dao.mapper

import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.data.dao.entity.MccCodePresetEntity

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
