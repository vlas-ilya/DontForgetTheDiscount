package su.tease.project.feature.cashback.domain.entity

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset

@Parcelize
data class CashBackOwner(
    val id: String,
    val preset: CashBackOwnerPreset,
    val customName: String,
    val cashBacks: PersistentList<CashBack>,
) : Parcelable
