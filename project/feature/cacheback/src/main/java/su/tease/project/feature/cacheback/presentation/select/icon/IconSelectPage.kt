package su.tease.project.feature.cacheback.presentation.select.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.feature.cacheback.R
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor

class IconSelectPage(
    store: Store<*>,
    val target: Target,
    private val dictionaryInterceptor: DictionaryInterceptor,
) : BasePageComponent(store) {

    @Composable
    override operator fun invoke() {
        LaunchedEffect(Unit) { rootConfig { copy(isFullscreen = true) } }
        LaunchedEffect(Unit) { appConfig { copy(titleRes = R.string.page_select_cache_back_icon_title) } }

        val icons by memoize { dictionaryInterceptor.cacheBacksIcons() }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = Theme.sizes.size40),
            modifier = Modifier.padding(
                horizontal = Theme.sizes.padding6,
                vertical = Theme.sizes.padding8
            )
        ) {
            icons?.forEach {
                item(key = it.url) {
                    DFImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Theme.sizes.round4))
                            .clickable {
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            }
                            .padding(Theme.sizes.padding4)
                            .size(Theme.sizes.size32),
                        url = it.url,
                        contentDescription = "",
                        tint = Theme.colors.iconTint,
                    )
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
