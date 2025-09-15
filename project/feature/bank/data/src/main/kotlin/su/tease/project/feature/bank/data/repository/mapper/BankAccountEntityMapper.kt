package su.tease.project.feature.bank.data.repository.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.bank.data.dao.entity.BankAccountEntity
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.BankPreset
import su.tease.project.feature.bank.domain.entity.CashBack

fun BankAccount.toEntity() = BankAccountEntity(
    id = id,
    presetId = preset.id,
    customName = customName,
)

fun BankAccountEntity.toDomain(
    bankPreset: BankPreset,
    cashBacks: PersistentList<CashBack>,
) = BankAccount(
    id = id,
    preset = bankPreset,
    customName = customName,
    cashBacks = cashBacks,
)
