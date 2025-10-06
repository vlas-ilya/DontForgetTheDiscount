package su.tease.project.feature.info.presentation.dependencies.navigation

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget

@Parcelize
sealed class Info: NavigationTarget.Page
data object Banks : Info()
data object BankPresets : Info()
data object BankIconPresets : Info()
data object Shops : Info()
data object ShopPresets : Info()
data object ShopIconPresets : Info()
data object CashBackPresets : Info()
data object CashBackIconPresets : Info()
data object MccCodePresets : Info()
