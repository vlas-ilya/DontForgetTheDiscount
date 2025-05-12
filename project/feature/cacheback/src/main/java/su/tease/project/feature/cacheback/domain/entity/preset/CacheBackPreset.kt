package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CacheBackPreset(
    val id: String,
    val name: String,
    val info: String,
    val iconPreset: CacheBackIconPreset,
    val bankPreset: BankPreset,
    val mccCodes: PersistentList<MccCodePreset>,
) : Parcelable
