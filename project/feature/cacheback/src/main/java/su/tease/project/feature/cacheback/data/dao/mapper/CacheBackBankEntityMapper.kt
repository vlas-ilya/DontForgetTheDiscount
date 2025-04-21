package su.tease.project.feature.cacheback.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.data.dao.entity.BankEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.BankIcon
import su.tease.project.feature.cacheback.domain.entity.BankId
import su.tease.project.feature.cacheback.domain.entity.BankName

fun BankEntity.toDomain(
    cacheBack: PersistentList<CacheBack>,
) = Bank(
    id = BankId(id),
    name = BankName(name),
    icon = BankIcon(iconUrl),
    cacheBacks = cacheBack,
)

fun Bank.toEntity() = BankEntity(
    id = id.value,
    name = name.value,
    iconUrl = icon.url,
)
