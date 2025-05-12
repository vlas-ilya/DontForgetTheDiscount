package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dao.entity.preset.BankPresetEntity
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.BankPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

fun BankPresetDto.toDomain() = BankPreset(
    id = id,
    name = name,
    iconPreset = icon.toDomain(),
)

fun BankPresetDto.toEntity() = BankPresetEntity(
    id = id,
    name = name,
    iconPresetId = icon.id,
)
