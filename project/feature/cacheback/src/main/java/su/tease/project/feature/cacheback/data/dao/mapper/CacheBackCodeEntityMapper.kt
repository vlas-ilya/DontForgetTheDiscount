package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodeEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeValue
import su.tease.project.feature.cacheback.domain.entity.CacheBackId

fun CacheBackCodeEntity.toDomain() = CacheBackCode(
    id = CacheBackCodeId(id),
    code = CacheBackCodeValue(code),
)

fun CacheBackCode.toDto(cacheBackId: CacheBackId) = CacheBackCodeEntity(
    id = id.value,
    code = code.code,
    cacheBackId = cacheBackId.value,
)
