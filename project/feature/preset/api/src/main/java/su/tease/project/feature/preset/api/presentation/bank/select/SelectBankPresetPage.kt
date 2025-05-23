package su.tease.project.feature.preset.api.presentation.bank.select

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.preset.api.domain.entity.BankPreset

interface SelectBankPresetPage {

    @Parcelize
    data class Target(
        val target: String,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: BankPreset?,
        ) = Target(T::class.java.name, selected)
    }
}
