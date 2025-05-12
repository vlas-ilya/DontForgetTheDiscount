package su.tease.project.feature.cacheback.data.dao.mapper.preset

import su.tease.project.feature.cacheback.data.dao.entity.preset.BankPresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

inline fun BankPresetEntity.toDomain(
    getIcon: (iconPresetId: String) -> BankIconPreset
) = BankPreset(
    id = id,
    name = name,
    iconPreset = getIcon(iconPresetId),
)

fun BankPreset.toEntity() = BankPresetEntity(
    id = id,
    name = name,
    iconPresetId = iconPreset.id,
)
