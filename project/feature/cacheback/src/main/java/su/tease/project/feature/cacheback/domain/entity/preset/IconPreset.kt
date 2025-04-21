package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@JvmInline
@Parcelize
value class IconPreset(
    val url: String
) : Parcelable
