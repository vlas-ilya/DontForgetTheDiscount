package su.tease.project.feature.cacheback.data.dataSource.mapper

import su.tease.project.feature.cacheback.data.dao.entity.preset.BankIconPresetEntity
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.BankIconPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset

fun BankIconPresetDto.toDomain() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankIconPresetDto.toEntity() = BankIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
