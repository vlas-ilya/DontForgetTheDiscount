package su.tease.project.feature.preset.impl.data.dao.mapper

import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity

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
