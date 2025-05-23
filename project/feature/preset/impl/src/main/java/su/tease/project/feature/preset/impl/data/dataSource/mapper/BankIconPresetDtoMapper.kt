package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.BankIconPresetDto

fun BankIconPresetDto.toDomain() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankIconPresetDto.toEntity() = BankIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
)
