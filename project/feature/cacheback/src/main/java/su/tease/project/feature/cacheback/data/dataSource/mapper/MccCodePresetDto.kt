package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.data.dao.entity.preset.MccCodePresetEntity
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.MccCodePresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

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
