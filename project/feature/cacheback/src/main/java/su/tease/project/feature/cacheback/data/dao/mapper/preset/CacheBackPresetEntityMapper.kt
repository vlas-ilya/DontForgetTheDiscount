package su.tease.project.feature.cacheback.data.dao.mapper.preset

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackPresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackPresetToMccCodePresetEntity
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

inline fun CacheBackPresetEntity.toDomain(
    getIconPreset: (iconPresetId: String) -> CacheBackIconPreset,
    getBankPreset: (bankPresetId: String) -> BankPreset,
    getCacheBackCodePresets: (cacheBackPresetId: String) -> PersistentList<MccCodePreset>,
) = CacheBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = getIconPreset(iconPresetId),
    bankPreset = getBankPreset(bankPresetId),
    mccCodes = getCacheBackCodePresets(id),
)

fun CacheBackPreset.toEntity() = CacheBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    bankPresetId = bankPreset.id,
)

fun CacheBackPreset.toMccCodeRelations(): PersistentList<CacheBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CacheBackPresetToMccCodePresetEntity(id, it.id) }

fun CacheBackPreset.toEntityWithRelations() = Pair(
    toEntity(),
    toMccCodeRelations(),
)
