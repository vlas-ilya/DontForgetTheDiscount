package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackIconPreset(
    val id: String,
    val iconUrl: String,
) : Parcelable
