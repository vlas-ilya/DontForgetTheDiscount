package su.tease.project.feature.bank.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankIconPreset(
    val id: String,
    val iconUrl: String,
) : Parcelable
