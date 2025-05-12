package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable

abstract class IconPreset(
    open val id: String,
    open val iconUrl: String,
) : Parcelable
