package su.tease.project.feature.cashback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset

@Parcelize
@Immutable
data class CashBack(
    val id: String,
    val cashBackPreset: CashBackPreset,
    val size: Int,
    val date: CashBackDate,
) : Parcelable

const val FRACTIONAL_SIZE = 2
