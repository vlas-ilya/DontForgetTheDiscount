package su.tease.project.feature.cashback.data.dao.mapper

import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

inline fun CashBackEntity.toDomain(
    getCashBackPreset: (cashBackPresetId: String) -> CashBackPreset
) = CashBack(
    id = id,
    cashBackPreset = getCashBackPreset(cashBackPresetId),
    size = size,
    date = CashBackDate(cashBackMonth, cashBackYear),
)

fun CashBack.toEntity(bankAccountId: String) = CashBackEntity(
    id = id,
    cashBackPresetId = cashBackPreset.id,
    size = size,
    cashBackMonth = date.month,
    cashBackYear = date.year,
    bankAccountId = bankAccountId,
)
