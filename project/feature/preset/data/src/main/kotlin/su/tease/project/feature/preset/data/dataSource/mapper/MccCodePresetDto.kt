package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.data.dataSource.dto.MccCodePresetDto
import su.tease.project.feature.preset.domain.entity.MccCodePreset

fun MccCodePresetDto.toDomain() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun List<MccCodePresetDto>.toDomain() =
    mapPersistent { it.toDomain() }

fun MccCodePresetDto.toEntity() = MccCodePresetEntity(
    id = id,
    code = code,
    name = name,
    info = info,
)
