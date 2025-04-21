package su.tease.project.feature.cacheback.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackBankEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank
import su.tease.project.feature.cacheback.domain.entity.CacheBackBankIcon
import su.tease.project.feature.cacheback.domain.entity.CacheBackBankId
import su.tease.project.feature.cacheback.domain.entity.CacheBackBankName

fun CacheBackBankEntity.toDomain(
    cacheBack: PersistentList<CacheBack>,
) = CacheBackBank(
    id = CacheBackBankId(id),
    name = CacheBackBankName(name),
    icon = CacheBackBankIcon(iconUrl),
    cacheBacks = cacheBack,
)

fun CacheBackBank.toDto() = CacheBackBankEntity(
    id = id.value,
    name = name.value,
    iconUrl = icon.url,
)
