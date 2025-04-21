package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dataSource.dto.IconPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

fun IconPresetDto.toDomain() = IconPreset(
    url = url,
)
