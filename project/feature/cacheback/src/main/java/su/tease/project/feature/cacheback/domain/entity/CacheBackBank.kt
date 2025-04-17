package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.clean.domain.entity.EntityId

@Parcelize
@Immutable
data class CacheBackBank(
    val id: CacheBackBankId,
    val name: CacheBackBankName,
    val icon: CacheBackBankIcon,
    val cacheBack: PersistentList<CacheBack>
) : Parcelable

@JvmInline
@Parcelize
value class CacheBackBankId(override val id: String) : EntityId<CacheBackId>, Parcelable

@JvmInline
@Parcelize
value class CacheBackBankIcon(val url: String) : Parcelable

@JvmInline
@Parcelize
value class CacheBackBankName(val value: String) : Parcelable
