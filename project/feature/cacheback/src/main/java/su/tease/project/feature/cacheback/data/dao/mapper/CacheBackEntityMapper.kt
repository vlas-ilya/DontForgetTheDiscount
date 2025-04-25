package su.tease.project.feature.cacheback.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.cacheback.domain.entity.BankId
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.CacheBackCode
import su.tease.project.feature.cacheback.domain.entity.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.CacheBackId
import su.tease.project.feature.cacheback.domain.entity.CacheBackInfo
import su.tease.project.feature.cacheback.domain.entity.CacheBackName
import su.tease.project.feature.cacheback.domain.entity.CacheBackSize

fun CacheBackEntity.toDomain(
    codes: PersistentList<CacheBackCode>
) = CacheBack(
    id = CacheBackId(id),
    name = CacheBackName(name),
    info = CacheBackInfo(info),
    icon = CacheBackIcon(iconUrl),
    size = CacheBackSize(size),
    codes = codes,
)

fun CacheBack.toEntity(bankId: BankId) = CacheBackEntity(
    id = id.value,
    name = name.value,
    info = info.value,
    iconUrl = icon.url,
    size = size.percent,
    cacheBackBankId = bankId.value,
)
