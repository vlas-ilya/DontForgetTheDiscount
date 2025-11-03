@file:Suppress("TooManyFunctions")

package su.tease.project.feature.bank.integration.mapper.cashback

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.bank.domain.entity.BankIconPreset as DomainBankIconPreset
import su.tease.project.feature.bank.domain.entity.BankPreset as DomainBankPreset
import su.tease.project.feature.bank.domain.entity.CashBack as DomainCashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate as DomainCashBackDate
import su.tease.project.feature.bank.domain.entity.CashBackIconPreset as DomainCashBackIconPreset
import su.tease.project.feature.bank.domain.entity.CashBackPreset as DomainCashBackPreset
import su.tease.project.feature.bank.domain.entity.MccCodePreset as DomainMccCodePreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset as PresetCashBackPreset
import su.tease.project.feature.preset.domain.entity.IconPreset as PresetIconPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset as PresetMccCodePreset

fun CashBackOwner.toDomain() = BankAccount(
    id = id,
    preset = preset.toDomain(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toDomain() },
)

fun CashBackOwnerPreset.toDomain() = DomainBankPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun CashBackOwnerIconPreset.toDomain() = DomainBankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun BankAccount.toExternal() = CashBackOwner(
    id = id,
    preset = preset.toExternal(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toExternal(id) },
)

fun DomainCashBack.toExternal(ownerId: String) = CashBack(
    id = id,
    preset = preset.toExternal(),
    size = size,
    date = date.toExternal(),
    ownerId = ownerId,
)

fun DomainCashBackPreset.toExternal() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toExternal(),
    mccCodes = mccCodes.mapPersistent { it.toExternal() },
)

fun DomainCashBackIconPreset.toExternal() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainBankPreset.toExternal() = CashBackOwnerPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun DomainBankIconPreset.toExternal() = CashBackOwnerIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainMccCodePreset.toExternal() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun DomainCashBackDate.toExternal() = CashBackDate(
    month = month,
    year = year,
)

fun CashBack.toDomain() = DomainCashBack(
    id = id,
    preset = preset.toDomain(),
    size = size,
    date = date.toDomain(),
)

fun CashBackPreset.toDomain() = DomainCashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    mccCodes = mccCodes.mapPersistent { it.toDomain() },
)

fun CashBackIconPreset.toDomain() = DomainCashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun MccCodePreset.toDomain() = DomainMccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun CashBackDate.toDomain() = DomainCashBackDate(
    month = month,
    year = year,
)

fun CashBackOwnerPreset.toPreset() = BankPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toPreset(),
)

fun CashBackOwnerIconPreset.toPreset() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun PresetCashBackPreset.toExternal() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toExternal(),
    mccCodes = mccCodes.mapPersistent { it.toExternal() },
)

private fun PresetIconPreset.toExternal() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

private fun PresetMccCodePreset.toExternal() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)