package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.clean.domain.entity.EntityId

@Parcelize
@Immutable
data class CacheBack(
    val id: CacheBackId,
    val name: CacheBackName,
    val info: CacheBackInfo,
    val icon: CacheBackIcon,
    val size: CacheBackSize,
    val codes: PersistentList<CacheBackCode>,
    val date: CacheBackDate,
) : Parcelable

@JvmInline
@Parcelize
value class CacheBackId(override val value: String) : EntityId<CacheBackId>, Parcelable

@JvmInline
@Parcelize
value class CacheBackName(val value: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackInfo(val value: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackIcon(val url: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackSize(val percent: Int) : Parcelable

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
