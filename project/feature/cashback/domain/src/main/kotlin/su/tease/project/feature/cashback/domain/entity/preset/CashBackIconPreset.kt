package su.tease.project.feature.cashback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackIconPreset(
    val id: String,
    val iconUrl: String,
) : Parcelable
