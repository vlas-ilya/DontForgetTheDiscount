package su.tease.project.feature.cashback.integration.dependencies.mapper.preset

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset as ExternalCashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset as ExternalCashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset as ExternalMccCodePreset

fun ExternalCashBackPreset.toDomain() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    mccCodes = mccCodes.mapPersistent { it.toDomain() },
)

fun ExternalCashBackIconPreset.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ExternalMccCodePreset.toDomain() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)
