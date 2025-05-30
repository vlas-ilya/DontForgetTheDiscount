package su.tease.project.feature.preset.api.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CashBackPreset(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CashBackIconPreset,
    val bankPreset: BankPreset,
    val mccCodes: PersistentList<MccCodePreset>,
) : Parcelable
