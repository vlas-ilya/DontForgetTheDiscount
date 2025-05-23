package su.tease.project.feature.preset.api.domain.entity

import android.os.Parcelable

abstract class IconPreset(
    open val id: String,
    open val iconUrl: String,
) : Parcelable
