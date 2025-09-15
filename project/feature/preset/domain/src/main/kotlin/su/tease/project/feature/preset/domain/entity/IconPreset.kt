package su.tease.project.feature.preset.domain.entity

import android.os.Parcelable

abstract class IconPreset(
    open val id: String,
    open val iconUrl: String,
) : Parcelable
