package su.tease.project.feature.cacheback.presentation.select.text

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.design.component.controls.page.DFPage

class TextSelectPage(
    store: Store<*>,
    val target: Target,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }
        DFPage(
            title = target.pageTitle,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) { }
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
