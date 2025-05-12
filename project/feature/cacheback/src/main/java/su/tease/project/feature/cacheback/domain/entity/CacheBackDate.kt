package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
@Suppress("MagicNumber")
data class CacheBackDate(
    val month: Int,
    val year: Int,
) : Parcelable {
    init {
        require(month in 0..11)
        require(year in 2025..3000)
    }
}

@Suppress("MagicNumber")
val defaultCacheBackDate = CacheBackDate(0, 2025)
