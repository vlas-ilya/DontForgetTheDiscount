package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopIconPreset(
    val id: String,
    val iconUrl: String,
) : Parcelable
