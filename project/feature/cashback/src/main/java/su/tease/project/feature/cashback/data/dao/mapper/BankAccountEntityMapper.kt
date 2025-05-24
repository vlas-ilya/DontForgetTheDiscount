package su.tease.project.feature.cashback.data.dao.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cashback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.preset.api.domain.entity.BankPreset

inline fun BankAccountEntity.toDomain(
    getBankPreset: (bankPresetId: String) -> BankPreset,
    getCashBacks: (bankId: String) -> PersistentList<CashBack>,
) = BankAccount(
    id = id,
    bankPreset = getBankPreset(bankPresetId),
    customName = customName,
    cashBacks = getCashBacks(id),
)

fun BankAccount.toEntity() = BankAccountEntity(
    id = id,
    bankPresetId = bankPreset.id,
    customName = customName,
)
