package su.tease.project.feature.preset.integration.dependencies.presentation.navigation

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.icon.presentation.SelectIconPage
import su.tease.project.feature.icon.presentation.action.SelectIconActions
import su.tease.project.feature.preset.presentation.icon.save.SaveIconPresetPage.SelectIconTarget
import su.tease.project.feature.preset.presentation.icon.save.action.IconOwner
import su.tease.project.feature.preset.presentation.icon.save.action.SaveIconPresetActions
import su.tease.project.feature.preset.presentation.icon.save.reducer.IconInfo

class SaveIconPresetPateInterceptor : Interceptor {

    override fun intercept(action: PlainAction): List<PlainAction> = when (action) {
        is NavigationAction.ForwardToPage -> onNavigate(action)
        is SelectIconActions.OnSelect -> onSelect(action)
        is SelectIconActions.OnFinish -> onFinish(action)
        else -> listOf()
    }

    private fun onNavigate(action: NavigationAction.ForwardToPage) = action
        .let { it.page as? SelectIconTarget }
        ?.toSelectIconPage()
        ?.let { NavigationAction.ForwardToPage(page = it).let(::listOf) }
        ?: listOf()

    private fun SelectIconTarget.toSelectIconPage(): SelectIconPage.Target = when (iconOwner) {
        IconOwner.ForBank -> SelectIconPage<IconOwner.ForBank>()
        IconOwner.ForCashback -> SelectIconPage<IconOwner.ForCashback>()
        IconOwner.ForShop -> SelectIconPage<IconOwner.ForShop>()
    }

    private fun onSelect(action: SelectIconActions.OnSelect) = when (action.target) {
        IconOwner.ForBank::class.java.name -> IconOwner.ForBank
        IconOwner.ForCashback::class.java.name -> IconOwner.ForCashback
        IconOwner.ForShop::class.java.name -> IconOwner.ForShop
        else -> null
    }
        ?.let { IconInfo(it, action.filePath) }
        ?.let { SaveIconPresetActions.OnIconSelected(it).let(::listOf) }
        ?: listOf()

    private fun onFinish(action: SelectIconActions.OnFinish) = when (action.target) {
        IconOwner.ForBank::class.java.name,
        IconOwner.ForCashback::class.java.name,
        IconOwner.ForShop::class.java.name -> SaveIconPresetActions.OnFinish.let(::listOf)

        else -> listOf()
    }
}
