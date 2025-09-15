package su.tease.project.feature.preset.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset

inline fun CashBackPresetEntity.toDomain(
    getIconPreset: (iconPresetId: String) -> CashBackIconPreset,
    getCashBackOwnerPreset: (cashBackOwnerPresetId: String) -> CashBackOwnerPreset,
    getCashBackCodePresets: (cashBackPresetId: String) -> PersistentList<MccCodePreset>,
) = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = getIconPreset(iconPresetId),
    cashBackOwnerPreset = getCashBackOwnerPreset(cashBackOwnerPresetId),
    mccCodes = getCashBackCodePresets(id),
)

fun CashBackPreset.toEntity() = CashBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    cashBackOwnerPresetId = cashBackOwnerPreset.id,
)

fun CashBackPreset.toMccCodeRelations(): PersistentList<CashBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CashBackPresetToMccCodePresetEntity(id, it.id) }

fun CashBackPreset.toEntityWithRelations() = Pair(
    toEntity(),
    toMccCodeRelations(),
)
