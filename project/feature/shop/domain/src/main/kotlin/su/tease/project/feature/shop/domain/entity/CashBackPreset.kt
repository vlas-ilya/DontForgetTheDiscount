package su.tease.project.feature.shop.domain.entity

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackPreset(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CashBackIconPreset,
    val mccCodes: PersistentList<MccCodePreset>,
) : Parcelable
