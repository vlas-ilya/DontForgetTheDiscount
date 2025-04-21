package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.clean.domain.entity.EntityId

@Parcelize
@Immutable
data class Bank(
    val id: BankId,
    val name: BankName,
    val icon: BankIcon,
    val cacheBacks: PersistentList<CacheBack>,
) : Parcelable

@JvmInline
@Parcelize
value class BankId(override val value: String) : EntityId<Bank>, Parcelable

@JvmInline
@Parcelize
value class BankIcon(val url: String) : Parcelable

@JvmInline
@Parcelize
value class BankName(val value: String) : Parcelable
