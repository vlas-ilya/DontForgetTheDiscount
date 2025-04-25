package su.tease.project.feature.cacheback.presentation.add.page

import androidx.compose.runtime.Composable
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor

class IconSelectPage(
    store: Store<*>,
    val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: IconPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: IconPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: IconPreset?,
        ) = Target(T::class.java.name, selected)
    }
}
