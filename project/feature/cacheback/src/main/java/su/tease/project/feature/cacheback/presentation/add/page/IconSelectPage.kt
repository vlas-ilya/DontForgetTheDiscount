package su.tease.project.feature.cacheback.presentation.add.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.component.utils.AppContainerConfiguration
import su.tease.core.mvi.component.utils.RootContainerConfiguration
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.presentation.add.page.BankSelectPage.OnSelectAction

class IconSelectPage(
    store: Store<*>,
    val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    override fun RootContainerConfiguration.configure() {
        isFullscreen = true
    }

    override fun AppContainerConfiguration.configure() {
        hasNavigationBar = false
    }

    @Composable
    override operator fun invoke() {
        val icons by memoize { dictionaryInterceptor.cacheBacksIcons() }
        DFPage(
            title = stringResource(R.string.choose_cache_back_icon),
            onBackPressed = ::back,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Theme.sizes.size40)
            ) {
                icons?.forEach {
                    item(key = it.url) {
                        DFImage(
                            modifier = Modifier
                                .clickable {
                                    dispatch(OnSelectAction(target.target, it))
                                    back()
                                }
                                .padding(Theme.sizes.padding4)
                                .size(Theme.sizes.size32),
                            url = it.url,
                            contentDescription = "",
                        )
                    }
                }
            }
        }
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
