package su.tease.project.feature.cashback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackOwnerIconPreset(
    val id: String,
    val iconUrl: String,
) : Parcelable
