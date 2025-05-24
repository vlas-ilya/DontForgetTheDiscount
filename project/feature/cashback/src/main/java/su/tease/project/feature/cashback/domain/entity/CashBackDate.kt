package su.tease.project.feature.cashback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CashBackDate(
    val month: Int,
    val year: Int,
) : Parcelable {
    init {
        require(month in monthInterval)
        require(year in yearInterval)
    }
}

@Suppress("MagicNumber")
val monthInterval = 0..11

@Suppress("MagicNumber")
val yearInterval = 2025..3000

@Suppress("MagicNumber")
val defaultCashBackDate = CashBackDate(0, 2025)
