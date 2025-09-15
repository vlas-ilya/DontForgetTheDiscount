package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.data.dao.mapper.BANK_PRESET_TYPE
import su.tease.project.feature.preset.data.dataSource.dto.BankPresetDto
import su.tease.project.feature.preset.domain.entity.BankPreset

fun BankPresetDto.toDomain() = BankPreset(
    id = id,
    name = name,
    iconPreset = icon.toDomain(),
)

fun BankPresetDto.toEntity() = CashBackOwnerPresetEntity(
    id = id,
    name = name,
    iconPresetId = icon.id,
    type = BANK_PRESET_TYPE,
)
