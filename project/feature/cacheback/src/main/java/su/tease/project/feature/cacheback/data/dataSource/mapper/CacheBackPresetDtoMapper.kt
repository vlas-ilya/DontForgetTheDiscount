package su.tease.project.feature.cacheback.data.dataSource.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackPresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackPresetToMccCodePresetEntity
import su.tease.project.feature.cacheback.data.dataSource.dto.preset.CacheBackPresetDto
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

fun CacheBackPresetDto.toDomain(bankPreset: BankPreset) = CacheBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    bankPreset = bankPreset,
    mccCodes = mccCodes.toDomain(),
)

fun CacheBackPresetDto.toEntity(bankPresetId: String) = CacheBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    bankPresetId = bankPresetId,
)

fun CacheBackPresetDto.toMccCodeRelations(): PersistentList<CacheBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CacheBackPresetToMccCodePresetEntity(id, it.id) }

fun CacheBackPresetDto.toEntityWithRelations(bankPresetId: String) = Pair(
    toEntity(bankPresetId),
    toMccCodeRelations(),
)
