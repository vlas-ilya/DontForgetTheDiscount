package su.tease.project.feature.cashback.data.dao.mapper

import su.tease.project.feature.cashback.data.dao.entity.CashBackDateEntity
import su.tease.project.feature.cashback.domain.entity.CashBackDate

fun CashBackDateEntity.toDomain() = CashBackDate(
    month = cashBackMonth,
    year = cashBackYear,
)

fun CashBackDate.toEntity() = CashBackDateEntity(
    cashBackMonth = month,
    cashBackYear = year,
)
