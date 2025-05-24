package su.tease.project.feature.cashback.domain.mapper

import su.tease.project.core.utils.date.MonthYear
import su.tease.project.feature.cashback.domain.entity.CashBackDate

fun CashBackDate.toMonthYear() = MonthYear(
    month = month,
    year = year,
)

fun MonthYear.toCashBackDate() = CashBackDate(
    month = month,
    year = year,
)
