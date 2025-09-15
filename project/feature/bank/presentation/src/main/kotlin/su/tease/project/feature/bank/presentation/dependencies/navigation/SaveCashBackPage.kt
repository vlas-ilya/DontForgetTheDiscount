package su.tease.project.feature.bank.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.entity.CashBackPreset

@Parcelize
data class SaveCashBackPage(
    val id: String? = null,
    val bankAccount: BankAccount? = null,
    val cashBackPreset: CashBackPreset? = null,
    val size: Int? = null,
    val date: CashBackDate? = null,
) : NavigationTarget.Page
