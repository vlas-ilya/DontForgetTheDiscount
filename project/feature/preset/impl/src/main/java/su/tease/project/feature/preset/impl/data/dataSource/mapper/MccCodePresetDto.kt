package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.MccCodePresetDto

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
