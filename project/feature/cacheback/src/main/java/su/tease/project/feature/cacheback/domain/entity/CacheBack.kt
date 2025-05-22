package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

@Parcelize
@Immutable
data class CacheBack(
    val id: String,
    val cacheBackPreset: CacheBackPreset,
    val size: Int,
    val date: CacheBackDate,
) : Parcelable

const val FRACTIONAL_SIZE = 2
