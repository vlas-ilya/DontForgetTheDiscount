package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.BankPresetDto

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
