package su.tease.project.feature.cacheback.domain.entity.preset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankPreset(
    val id: String,
    val name: String,
    val icon: IconPreset,
) : Parcelable
