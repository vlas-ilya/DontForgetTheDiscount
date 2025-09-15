package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBack(
    val id: String,
    val preset: CashBackPreset,
    val size: Int,
    val date: CashBackDate,
) : Parcelable

const val FRACTIONAL_SIZE = 2
