package su.tease.project.feature.preset.api.presentation.cacheback.select

import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset

interface SelectCacheBackPresetPage {

    @Parcelize
    data class Target(
        val target: String,
        val bankPreset: BankPreset,
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: CacheBackPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            bankPreset: BankPreset,
        ) = Target(T::class.java.name, bankPreset)
    }
}
