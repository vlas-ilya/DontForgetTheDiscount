package su.tease.project.feature.cacheback.domain.mapper

import su.tease.project.core.utils.date.MonthYear
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate

fun CacheBackDate.toMonthYear() = MonthYear(
    month = month,
    year = year,
)

fun MonthYear.toCacheBackDate() = CacheBackDate(
    month = month,
    year = year,
)
