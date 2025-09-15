package su.tease.project.feature.bank.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankPreset(
    val id: String,
    val name: String,
    val iconPreset: BankIconPreset,
) : Parcelable
