package su.tease.project.feature.bank.integration.mapper.preset

import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.bank.domain.entity.BankIconPreset as DomainBankIconPreset
import su.tease.project.feature.bank.domain.entity.BankPreset as DomainBankPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset as DomainCashBackIconPreset

fun BankPreset.toDomain() = DomainBankPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun BankIconPreset.toDomain() = DomainBankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainBankPreset.toExternal() = BankPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun DomainBankIconPreset.toExternal() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackOwnerIconPreset.toPreset() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainCashBackIconPreset.toPreset() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)
