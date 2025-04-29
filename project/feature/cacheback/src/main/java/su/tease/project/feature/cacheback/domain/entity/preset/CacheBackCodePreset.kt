package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeId
import su.tease.project.feature.cacheback.domain.entity.CacheBackCodeValue

@Parcelize
data class CacheBackCodePreset(
    val id: CacheBackCodeId,
    val code: CacheBackCodeValue,
    val usageCount: Int,
) : Parcelable
