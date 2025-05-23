package su.tease.project.feature.preset.impl.presentation.cacheback.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.api.presentation.cacheback.select.SelectCacheBackPresetPage
import su.tease.project.feature.preset.impl.R
import su.tease.project.feature.preset.impl.presentation.cacheback.save.SaveCacheBackPresetPage
import su.tease.project.feature.preset.impl.presentation.cacheback.select.component.CacheBackPresetPreview
import su.tease.project.feature.preset.impl.presentation.cacheback.select.component.dialog.CacheBackPresetInfoDialog
import su.tease.project.feature.preset.impl.presentation.cacheback.select.reducer.CacheBackPresetInfoDialogAction
import su.tease.project.feature.preset.impl.presentation.cacheback.select.reducer.SelectCacheBackPresetState
import su.tease.project.design.icons.R as RIcons

class SelectCacheBackPresetPageImpl(
    store: Store<*>,
    private val target: SelectCacheBackPresetPage.Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<SelectCacheBackPresetPage.Target>(store), SelectCacheBackPresetPage {

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val cacheBacks by memoize { presetInterceptor.cacheBackPresets(target.bankPreset.id) }
        val savedCacheBackPreset = selectAsState(SelectCacheBackPresetState::savedCacheBackPreset).value

        LaunchedEffect(savedCacheBackPreset) {
            if (savedCacheBackPreset != null) {
                dispatch(SelectCacheBackPresetPage.OnSelectAction(target.target, savedCacheBackPreset))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = RIcons.drawable.plus,
                    onClick = { SaveCacheBackPresetPage(target.bankPreset).forward() }
                )
            )
        }

        DFPage(
            title = stringResource(R.string.page_cache_back_preset_list_title),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            if (cacheBacks == null) {
                SelectCacheBackPresetShimmer()
                return@DFPage
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
            ) {
                cacheBacks?.forEach {
                    item(key = it.id) {
                        CacheBackPresetPreview(
                            store = store,
                            cacheBackPreset = it,
                            onClick = {
                                dispatch(SelectCacheBackPresetPage.OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        CacheBackPresetInfoDialog(
            info = selectAsState(SelectCacheBackPresetState::shownCacheBack),
            onHide = { dispatch(CacheBackPresetInfoDialogAction.OnHide) },
        )
    }

    @Composable
    private fun SelectCacheBackPresetShimmer(modifier: Modifier = Modifier) {
        Shimmer(
            modifier = modifier,
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding8))
                repeat(SHIMMER_ITEM_COUNT) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = Theme.sizes.padding8)
                            .clip(RoundedCornerShape(Theme.sizes.round12))
                            .fillMaxWidth()
                            .height(Theme.sizes.size46)
                            .background(Theme.colors.shimmer)
                    )
                }
            }
        }
    }
}

private const val SHIMMER_ITEM_COUNT = 20
