package su.tease.project.feature.bank.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MccCodePreset(
    val id: String,
    val code: String,
    val name: String,
    val info: String,
) : Parcelable
