package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopPreset(
    val id: String,
    val name: String,
    val iconPreset: ShopIconPreset,
) : Parcelable
