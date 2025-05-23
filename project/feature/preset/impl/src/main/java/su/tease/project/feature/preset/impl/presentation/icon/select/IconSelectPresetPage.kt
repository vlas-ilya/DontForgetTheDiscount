package su.tease.project.feature.preset.impl.presentation.icon.select

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.image.DFImage
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.feature.preset.api.domain.entity.IconPreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor

class IconSelectPresetPage(
    store: Store<*>,
    val target: Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<IconSelectPresetPage.Target>(store) {

    @Composable
    override operator fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val icons by memoize { target.iconType.getIcons(presetInterceptor) }

        DFPage(
            title = stringResource(target.pageTitle),
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Theme.sizes.size40),
                contentPadding = PaddingValues(Theme.sizes.padding6),
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                horizontalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
            ) {
                icons?.forEach {
                    item(key = it.iconUrl) {
                        DFImage(
                            modifier = Modifier
                                .clip(target.iconType.clip())
                                .clickable {
                                    dispatch(OnSelectAction(target.target, it))
                                    back()
                                }
                                .padding(target.iconType.padding())
                                .size(target.iconType.size()),
                            url = it.iconUrl,
                            contentDescription = "",
                            tint = target.iconType.tint(),
                        )
                    }
                }
            }
        }
    }

    enum class IconType(
        val getIcons: suspend (PresetInterceptor) -> PersistentList<IconPreset>,
        val tint: @Composable () -> Color?,
        val clip: @Composable () -> Shape,
        val padding: @Composable () -> Dp,
        val size: @Composable () -> Dp,
    ) {
        CACHE_BACK_ICON(
            getIcons = PresetInterceptor::cacheBacksIconPresets,
            tint = { Theme.colors.iconTint },
            clip = { RoundedCornerShape(Theme.sizes.round4) },
            padding = { Theme.sizes.padding4 },
            size = { Theme.sizes.size46 },
        ),
        BANK_ICON(
            getIcons = PresetInterceptor::bankIconPresets,
            tint = { null },
            clip = { CircleShape },
            padding = { 0.dp },
            size = { Theme.sizes.size46 },
        )
    }

    @Parcelize
    data class Target(
        val target: String,
        val iconType: IconType,
        @StringRes val pageTitle: Int,
        val selected: IconPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: IconPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            iconType: IconType,
            @StringRes pageTitle: Int,
            selected: IconPreset?,
        ) = Target(T::class.java.name, iconType, pageTitle, selected)
    }
}
