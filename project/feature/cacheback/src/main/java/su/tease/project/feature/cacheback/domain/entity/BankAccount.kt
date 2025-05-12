package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

@Parcelize
@Immutable
data class BankAccount(
    val id: String,
    val bankPreset: BankPreset,
    val customName: String,
    val cacheBacks: PersistentList<CacheBack>,
) : Parcelable
