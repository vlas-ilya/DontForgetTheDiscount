package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.CacheBackDateEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

fun CacheBackDateEntity.toDomain() = CacheBackDate(
    month = cacheBackMonth,
    year = cacheBackYear,
)

fun CacheBackDate.toEntity() = CacheBackDateEntity(
    cacheBackMonth = month,
    cacheBackYear = year,
)
