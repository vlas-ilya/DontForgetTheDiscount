package su.tease.project.feature.cacheback.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBack
import su.tease.project.feature.preset.api.domain.entity.BankPreset

inline fun BankAccountEntity.toDomain(
    getBankPreset: (bankPresetId: String) -> BankPreset,
    getCacheBacks: (bankId: String) -> PersistentList<CacheBack>,
) = BankAccount(
    id = id,
    bankPreset = getBankPreset(bankPresetId),
    customName = customName,
    cacheBacks = getCacheBacks(id),
)

fun BankAccount.toEntity() = BankAccountEntity(
    id = id,
    bankPresetId = bankPreset.id,
    customName = customName,
)
