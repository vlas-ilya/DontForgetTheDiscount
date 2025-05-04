package su.tease.project.feature.cacheback.presentation.select.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store

class TextSelectPage(
    store: Store<*>,
    val target: Target,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) { rootConfig { copy(isFullscreen = true) } }
        LaunchedEffect(Unit) { appConfig { copy(title = target.pageTitle) } }
    }

    @Parcelize
    data class Target(
        val target: String,
        val pageTitle: String,
        val name: String,
        val text: String,
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val name: String,
        val text: String,
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            pageTitle: String,
            name: String,
            text: String,
        ) = Target(T::class.java.name, pageTitle, name, text)
    }
}
