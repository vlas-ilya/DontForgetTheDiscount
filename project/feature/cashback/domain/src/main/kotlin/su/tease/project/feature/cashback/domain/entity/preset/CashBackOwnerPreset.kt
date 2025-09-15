package su.tease.project.feature.cashback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackOwnerPreset(
    val id: String,
    val name: String,
    val iconPreset: CashBackOwnerIconPreset,
) : Parcelable
