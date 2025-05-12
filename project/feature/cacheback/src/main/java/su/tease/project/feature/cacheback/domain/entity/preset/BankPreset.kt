package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class BankPreset(
    val id: String,
    val name: String,
    val iconPreset: BankIconPreset,
) : Parcelable
