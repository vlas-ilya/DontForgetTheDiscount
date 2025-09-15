package su.tease.project.feature.shop.presentation.list.utils

import su.tease.project.core.utils.date.MonthYear
import su.tease.project.feature.shop.domain.entity.CashBackDate

fun CashBackDate.toMonthYear() = MonthYear(
    month = month,
    year = year,
)

fun MonthYear.toCashBackDate() = CashBackDate(
    month = month,
    year = year,
)
