package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

inline fun CacheBackEntity.toDomain(
    getCacheBackPreset: (cacheBackPresetId: String) -> CacheBackPreset
) = CacheBack(
    id = id,
    cacheBackPreset = getCacheBackPreset(cacheBackPresetId),
    size = size,
    date = CacheBackDate(cacheBackMonth, cacheBackYear),
)

fun CacheBack.toEntity(bankAccountId: String) = CacheBackEntity(
    id = id,
    cacheBackPresetId = cacheBackPreset.id,
    size = size,
    cacheBackMonth = date.month,
    cacheBackYear = date.year,
    bankAccountId = bankAccountId,
)
