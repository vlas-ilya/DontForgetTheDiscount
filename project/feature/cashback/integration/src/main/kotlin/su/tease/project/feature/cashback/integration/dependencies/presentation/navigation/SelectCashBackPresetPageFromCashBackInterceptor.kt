package su.tease.project.feature.cashback.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.toExternal
import su.tease.project.feature.cashback.integration.dependencies.mapper.preset.toDomain
import su.tease.project.feature.cashback.presentation.dependencies.navigation.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage as ExternalSelectCashBackPresetPage

class SelectCashBackPresetPageFromCashBackInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is NavigationAction.ForwardToPage -> tryToNavigateToSelectBankPresetPage(action)
        is ExternalSelectCashBackPresetPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        else -> listOf()
    }

    private fun tryToNavigateToSelectBankPresetPage(action: NavigationAction.ForwardToPage): List<PlainAction> {
        if (action.page !is SelectCashBackPresetPage) return listOf()
        val page = action.page as SelectCashBackPresetPage
        return NavigationAction.ForwardToPage(
            page = ExternalSelectCashBackPresetPage<Target>(page.ownerPreset.toExternal()),
            singleTop = action.singleTop,
        ).let(::listOf)
    }

    private fun tryToHandleOnSelectAction(action: ExternalSelectCashBackPresetPage.OnSelectAction): List<PlainAction> {
        if (!action.target.current()) return listOf()
        return listOf(
            action,
            SelectCashBackPresetPage.OnSelectAction(action.selected?.toDomain())
        )
    }

    private fun String.current() = this == Target::class.java.name

    private object Target
}
