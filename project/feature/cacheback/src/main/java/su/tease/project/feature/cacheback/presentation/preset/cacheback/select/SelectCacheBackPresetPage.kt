package su.tease.project.feature.cacheback.presentation.preset.cacheback.select

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.utils.memoize
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.icons.R
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.interceptor.PresetInterceptor
import su.tease.project.feature.cacheback.presentation.preset.cacheback.save.SaveCacheBackPresetPage
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.component.CacheBackPresetPreview
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.component.dialog.CacheBackPresetInfoDialog
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.CacheBackPresetInfoDialogAction
import su.tease.project.feature.cacheback.presentation.preset.cacheback.select.reducer.SelectCacheBackPresetState

class SelectCacheBackPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInterceptor: PresetInterceptor,
) : BasePageComponent<SelectCacheBackPresetPage.Target>(store) {

    @Composable
    override fun invoke() {
        RootConfig { copy(isFullscreen = true) }

        val cacheBacks by memoize { presetInterceptor.cacheBackPresets(target.bankPreset.id) }
        val savedCacheBackPreset = selectAsState(SelectCacheBackPresetState::savedCacheBackPreset).value

        LaunchedEffect(savedCacheBackPreset) {
            if (savedCacheBackPreset != null) {
                dispatch(OnSelectAction(target.target, savedCacheBackPreset))
                back()
            }
        }

        val floatingButtons = remember {
            persistentListOf(
                DFPageFloatingButton(
                    icon = R.drawable.plus,
                    onClick = { SaveCacheBackPresetPage(target.bankPreset).forward() }
                )
            )
        }

        DFPage(
            title = stringResource(target.pageTitle),
            floatingButtons = floatingButtons,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
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
                                dispatch(OnSelectAction(target.target, it))
                                back()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.sizes.padding8),
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

    @Parcelize
    data class Target(
        val target: String,
        @StringRes val pageTitle: Int,
        val bankPreset: BankPreset,
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: CacheBackPreset?
    ) : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            @StringRes pageTitle: Int,
            bankPreset: BankPreset,
        ) = Target(T::class.java.name, pageTitle, bankPreset)
    }
}
