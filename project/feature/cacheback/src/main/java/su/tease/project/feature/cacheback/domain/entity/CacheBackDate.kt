package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class CacheBackDate(
    val month: Int,
    val year: Int,
) : Parcelable {
    init {
        require(month in monthInterval)
        require(year in yearInterval)
    }
}

@Suppress("MagicNumber")
val monthInterval = 0..11

@Suppress("MagicNumber")
val yearInterval = 2025..3000

@Suppress("MagicNumber")
val defaultCacheBackDate = CacheBackDate(0, 2025)
