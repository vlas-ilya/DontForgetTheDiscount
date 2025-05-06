package su.tease.project.core.utils.date.impl

import android.content.Context
import kotlinx.collections.immutable.persistentListOf
import su.tease.project.core.utils.R
import su.tease.project.core.utils.date.DateProvider
import su.tease.project.core.utils.date.MonthYear
import java.util.Calendar

class DateProviderImpl(
    private val context: Context,
    private val calendar: Calendar,
) : DateProvider {

    override fun current() = calendar
        .let { MonthYear(it.get(Calendar.MONTH), it.get(Calendar.YEAR)) }

    override fun next() = calendar
        .apply { add(Calendar.MONTH, 1) }
        .let { MonthYear(it.get(Calendar.MONTH), it.get(Calendar.YEAR)) }

    override fun currentAndNext() = persistentListOf(current(), next())

    override fun toText(monthYear: MonthYear) = context.getString(
        R.string.date,
        context.getString(monthYear.month.toResource()),
        monthYear.year
    )

    @Suppress("MagicNumber")
    private fun Int.toResource(): Int = when (this) {
        0 -> R.string.january
        1 -> R.string.february
        2 -> R.string.march
        3 -> R.string.april
        4 -> R.string.may
        5 -> R.string.june
        6 -> R.string.july
        7 -> R.string.august
        8 -> R.string.september
        9 -> R.string.october
        10 -> R.string.november
        11 -> R.string.december
        else -> error("No such month")
    }
}
