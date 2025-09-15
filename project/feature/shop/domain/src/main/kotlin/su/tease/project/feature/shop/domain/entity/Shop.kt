package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val id: String,
    val preset: ShopPreset,
    val customName: String,
    val cashBacks: PersistentList<CashBack>,
) : Parcelable
