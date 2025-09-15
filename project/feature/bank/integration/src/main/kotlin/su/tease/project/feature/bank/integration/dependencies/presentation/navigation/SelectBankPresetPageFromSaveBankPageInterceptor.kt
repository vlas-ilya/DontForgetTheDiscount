package su.tease.project.feature.bank.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.bank.integration.mapper.preset.toDomain
import su.tease.project.feature.bank.integration.mapper.preset.toExternal
import su.tease.project.feature.bank.presentation.dependencies.navigation.SelectBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.SelectBankPresetPage as ExternalSelectBankPresetPage

class SelectBankPresetPageFromSaveBankPageInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is NavigationAction.ForwardToPage -> tryToNavigateToSelectBankPresetPage(action)
        is ExternalSelectBankPresetPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        else -> listOf()
    }

    private fun tryToNavigateToSelectBankPresetPage(action: NavigationAction.ForwardToPage): List<PlainAction> {
        if (action.page !is SelectBankPresetPage) return listOf()
        val page = action.page as SelectBankPresetPage
        return NavigationAction.ForwardToPage(
            page = ExternalSelectBankPresetPage<Target>(
                selected = page.selected?.toExternal(),
            ),
            singleTop = action.singleTop,
        ).let(::listOf)
    }

    private fun tryToHandleOnSelectAction(action: ExternalSelectBankPresetPage.OnSelectAction): List<PlainAction> {
        if (!action.target.current()) return listOf()
        return listOf(
            action,
            SelectBankPresetPage.OnSelectAction(action.selected?.toDomain())
        )
    }

    private fun String.current() = this == Target::class.java.name

    private object Target
}
