package su.tease.project.feature.cacheback.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.core.clean.domain.entity.EntityId

@Parcelize
data class CacheBackCode(
    val id: CacheBackCodeId,
    val code: CacheBackCodeValue,
) : Parcelable


@JvmInline
@Parcelize
value class CacheBackCodeId(override val value: String) : EntityId<CacheBackCode>, Parcelable


@JvmInline
@Parcelize
value class CacheBackCodeValue(val code: String) : Parcelable