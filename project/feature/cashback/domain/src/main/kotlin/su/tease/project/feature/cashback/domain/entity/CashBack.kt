package su.tease.project.feature.cashback.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

@Parcelize
data class CashBack(
    val id: String,
    val preset: CashBackPreset,
    val size: Int,
    val date: CashBackDate,
    val ownerId: String,
) : Parcelable

const val FRACTIONAL_SIZE = 2
