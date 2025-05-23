package su.tease.project.feature.preset.api.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class MccCodePreset(
    val id: String,
    val code: String,
    val name: String,
    val info: String,
) : Parcelable
