package su.tease.project.feature.preset.impl.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetToMccCodePresetEntity

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
