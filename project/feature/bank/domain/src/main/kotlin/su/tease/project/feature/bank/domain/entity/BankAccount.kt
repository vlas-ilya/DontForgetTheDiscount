package su.tease.project.feature.bank.domain.entity

import android.os.Parcelable
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankAccount(
    val id: String,
    val preset: BankPreset,
    val customName: String,
    val cashBacks: PersistentList<CashBack>,
) : Parcelable
