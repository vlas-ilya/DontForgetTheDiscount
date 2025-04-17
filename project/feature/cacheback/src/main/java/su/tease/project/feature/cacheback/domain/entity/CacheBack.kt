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
    val codes: PersistentList<CacheBackCode>
) : Parcelable

@JvmInline
@Parcelize
value class CacheBackId(override val id: String) : EntityId<CacheBackId>, Parcelable

@JvmInline
@Parcelize
value class CacheBackName(val value: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackInfo(val value: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackIcon(val value: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackSize(val percent: Int) : Parcelable

@JvmInline
@Parcelize
value class CacheBackCode(val code: String) : Parcelable
