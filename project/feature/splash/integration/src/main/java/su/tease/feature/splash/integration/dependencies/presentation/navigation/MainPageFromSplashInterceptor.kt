package su.tease.feature.splash.integration.dependencies.presentation.navigation

import ru.tease.project.feature.splash.presentation.dependencies.navigation.MainPage
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.intercetpor.Interceptor
import su.tease.project.core.mvi.navigation.action.NavigationAction
import su.tease.project.feature.main.presentation.MainApp

class MainPageFromSplashInterceptor : Interceptor {

    override fun intercept(action: PlainAction) = when (action) {
        is NavigationAction.ForwardToPage -> tryToNavigateToMainApp(action)
        else -> listOf()
    }

    private fun tryToNavigateToMainApp(action: NavigationAction.ForwardToPage): List<PlainAction> {
        if (action.page !is MainPage) return listOf()
        return listOf(NavigationAction.ReplaceApp(MainApp()))
    }
}
