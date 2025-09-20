package su.tease.project.feature.cashback.presentation.dependencies

import androidx.compose.runtime.Immutable

@Immutable
interface CashBackOwnerTypeProvider {
    fun get(): String
}