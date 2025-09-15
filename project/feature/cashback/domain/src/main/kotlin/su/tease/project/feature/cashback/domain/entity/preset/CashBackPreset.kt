package su.tease.project.feature.cashback.domain.entity.preset

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
class CashBackPreset(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CashBackIconPreset,
    val mccCodes: PersistentList<MccCodePreset>,
) : Parcelable
