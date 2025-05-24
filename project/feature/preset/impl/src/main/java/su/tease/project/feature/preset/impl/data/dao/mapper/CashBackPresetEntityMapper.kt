package su.tease.project.feature.preset.impl.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetToMccCodePresetEntity

inline fun CashBackPresetEntity.toDomain(
    getIconPreset: (iconPresetId: String) -> CashBackIconPreset,
    getBankPreset: (bankPresetId: String) -> BankPreset,
    getCashBackCodePresets: (cashBackPresetId: String) -> PersistentList<MccCodePreset>,
) = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = getIconPreset(iconPresetId),
    bankPreset = getBankPreset(bankPresetId),
    mccCodes = getCashBackCodePresets(id),
)

fun CashBackPreset.toEntity() = CashBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    bankPresetId = bankPreset.id,
)

fun CashBackPreset.toMccCodeRelations(): PersistentList<CashBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CashBackPresetToMccCodePresetEntity(id, it.id) }

fun CashBackPreset.toEntityWithRelations() = Pair(
    toEntity(),
    toMccCodeRelations(),
)
