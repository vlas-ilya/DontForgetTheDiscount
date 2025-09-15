package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
