package su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.bank
import su.tease.project.core.utils.ext.mapPersistent
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
import su.tease.project.feature.bank.domain.entity.BankAccount as ExternalBankAccount
import su.tease.project.feature.bank.domain.entity.BankIconPreset as ExternalBankIconPreset
import su.tease.project.feature.bank.domain.entity.BankPreset as ExternalBankPreset
import su.tease.project.feature.bank.domain.entity.CashBack as ExternalBankCashBack
import su.tease.project.feature.bank.domain.entity.CashBackDate as ExternalBankCashBackDate
import su.tease.project.feature.bank.domain.entity.CashBackIconPreset as ExternalBankCashBackIconPreset
import su.tease.project.feature.bank.domain.entity.CashBackPreset as ExternalBankCashBackPreset
import su.tease.project.feature.bank.domain.entity.MccCodePreset as ExternalBankMccCodePreset


fun ExternalBankAccount.toDomain() = CashBackOwner(
    id = id,
    preset = preset.toDomain(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toDomain(id) },
)

fun ExternalBankPreset.toDomain() = CashBackOwnerPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun ExternalBankIconPreset.toDomain() = CashBackOwnerIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ExternalBankCashBack.toDomain(ownerId: String) = CashBack(
    id = id,
    preset = preset.toDomain(),
    size = size,
    date = date.toDomain(),
    ownerId = ownerId,
)

fun ExternalBankCashBackPreset.toDomain() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    mccCodes = mccCodes.mapPersistent { it.toDomain() },
)

fun ExternalBankCashBackIconPreset.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ExternalBankMccCodePreset.toDomain() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun ExternalBankCashBackDate.toDomain() = CashBackDate(
    month = month,
    year = year,
)

fun CashBackOwnerPreset.toExternalBankPreset() = BankPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun CashBackOwnerIconPreset.toExternal() = BankIconPreset(
    id = id,
    iconUrl = iconUrl,
)
