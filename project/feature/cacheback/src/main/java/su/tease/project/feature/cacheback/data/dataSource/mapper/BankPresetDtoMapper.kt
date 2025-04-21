package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dataSource.dto.BankPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

fun BankPresetDto.toDomain() = BankPreset(
    id = id,
    name = name,
    icon = icon.toDomain(),
)
