package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerIconPresetEntity
import su.tease.project.feature.preset.data.dao.mapper.BANK_ICON_PRESET_TYPE
import su.tease.project.feature.preset.data.dataSource.dto.BankIconPresetDto
import su.tease.project.feature.preset.domain.entity.BankIconPreset

fun BankIconPresetDto.toDomain() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankIconPresetDto.toEntity() = CashBackOwnerIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
    type = BANK_ICON_PRESET_TYPE
)
