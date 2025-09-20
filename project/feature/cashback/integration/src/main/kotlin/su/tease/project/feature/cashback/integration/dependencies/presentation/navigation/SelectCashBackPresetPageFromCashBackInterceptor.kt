package su.tease.project.feature.cashback.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.bank.toExternalBankPreset
import su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.shop.toExternalShopPreset
import su.tease.project.feature.cashback.integration.dependencies.mapper.preset.toDomain
import su.tease.project.feature.cashback.presentation.dependencies.navigation.SelectCashBackPresetPage
import su.tease.project.feature.preset.presentation.cashback.select.SelectCashBackPresetPage as ExternalSelectCashBackPresetPage

class SelectCashBackPresetPageFromCashBackInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is NavigationAction.ForwardToPage -> tryToNavigateToSelectOwnerPresetPage(action)
        is ExternalSelectCashBackPresetPage.OnSelectAction -> tryToHandleOnSelectAction(action)
        else -> listOf()
    }

    private fun tryToNavigateToSelectOwnerPresetPage(action: NavigationAction.ForwardToPage): List<PlainAction> {
        if (action.page !is SelectCashBackPresetPage) return listOf()
        val page = action.page as SelectCashBackPresetPage
        val ownerPreset = when (page.ownerType) {
            "bank" -> page.ownerPreset.toExternalBankPreset()
            "shop" -> page.ownerPreset.toExternalShopPreset()
            else -> error("Unsupported owner type")
        }
        return NavigationAction.ForwardToPage(
            page = ExternalSelectCashBackPresetPage<Target>(ownerPreset),
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
