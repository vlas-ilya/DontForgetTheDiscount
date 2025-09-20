package su.tease.project.feature.shop.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.shop.integration.mapper.preset.toDomain
import su.tease.project.feature.shop.integration.mapper.preset.toExternal
import su.tease.project.feature.shop.presentation.dependencies.navigation.SelectShopPresetPage
import su.tease.project.feature.preset.presentation.shop.select.SelectShopPresetPage as ExternalSelectShopPresetPage

class SelectShopPresetPageFromSaveShopPageInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is NavigationAction.ForwardToPage -> tryToNavigateToSelectShopPresetPage(action)
        is ExternalSelectShopPresetPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        else -> listOf()
    }

    private fun tryToNavigateToSelectShopPresetPage(action: NavigationAction.ForwardToPage): List<PlainAction> {
        if (action.page !is SelectShopPresetPage) return listOf()
        val page = action.page as SelectShopPresetPage
        return NavigationAction.ForwardToPage(
            page = ExternalSelectShopPresetPage<Target>(
                selected = page.selected?.toExternal(),
            ),
            singleTop = action.singleTop,
        ).let(::listOf)
    }

    private fun tryToHandleOnSelectAction(action: ExternalSelectShopPresetPage.OnSelectAction): List<PlainAction> {
        if (!action.target.current()) return listOf()
        return listOf(
            action,
            SelectShopPresetPage.OnSelectAction(action.selected?.toDomain())
        )
    }

    private fun String.current() = this == Target::class.java.name

    private object Target
}
