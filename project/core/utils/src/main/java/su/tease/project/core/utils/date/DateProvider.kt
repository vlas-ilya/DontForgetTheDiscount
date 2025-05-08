package su.tease.project.core.utils.date

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList

@Immutable
interface DateProvider {
    fun current(): MonthYear

    fun next(): MonthYear

    fun currentAndNext(): PersistentList<MonthYear>

    fun toText(monthYear: MonthYear): String
}

data class MonthYear(
    val month: Int,
    val year: Int,
)
