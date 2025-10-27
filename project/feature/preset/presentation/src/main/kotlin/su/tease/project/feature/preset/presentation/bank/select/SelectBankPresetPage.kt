package su.tease.project.feature.preset.presentation.bank.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.utils.ext.map
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.memoize
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.list.LazyList
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.design.component.controls.shimmer.Shimmer
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.presentation.R
import su.tease.project.feature.preset.presentation.bank.component.BankPresetPreview
import su.tease.project.feature.preset.presentation.bank.save.SaveBankPresetPage
import su.tease.project.feature.preset.presentation.bank.select.reducer.SelectBankPresetState
import su.tease.project.feature.preset.presentation.bank.utils.toUi
import su.tease.project.design.icons.R as RIcons

class SelectBankPresetPage(
    store: Store<*>,
    private val target: Target,
    private val presetInteractor: PresetInteractor,
    private val resourceProvider: ResourceProvider,
) : BasePageComponent<SelectBankPresetPage.Target>(store) {

    init {
        dispatch(OnInit)
    }

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    @Composable
    override operator fun invoke() {
        val savedBankPreset = selectAsState(SelectBankPresetState::savedCashBackOwnerPreset).value
        val list = memoize { presetInteractor.bankPresets() }
            .map { it?.toUi(store, ::onBankPresetClick) }

        LaunchedEffect(savedBankPreset) {
            if (savedBankPreset != null) {
                dispatch(OnSelectAction(target.target, savedBankPreset))
                back()
            }
        }

        val isScrollTopButtonVisible = remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON
            }
        }

        val (_, _, resetScroll) = scrollDirectionState

        val scope = rememberCoroutineScope()
        val scrollUp = rememberCallback(resetScroll, lazyListState) {
            scope.launch {
                resetScroll()
                lazyListState.animateScrollToItem(0)
            }
        }

        val floatingButtons = remember {
            derivedStateOf {
                persistentListOf(
                    DFPageFloatingButton(
                        icon = RIcons.drawable.plus,
                        onClick = { SaveBankPresetPage().forward() }
                    ),
                    DFPageFloatingButton(
                        icon = RIcons.drawable.angle_up,
                        onClick = { scrollUp() },
                        type = DFPageFloatingButton.Type.GRAY,
                        isVisible = isScrollTopButtonVisible.value
                    )
                )
            }
        }

        DFPage(
            title = stringResource(R.string.Presets_SelectBankPreset_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
        ) {
            val banks = list.value ?: run {
                BankPresetShimmer()
                return@DFPage
            }

            LazyList(
                count = banks.size,
                modifier = Modifier.fillMaxWidth(),
                itemContent = banks::get,
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4),
                contentPadding = PaddingValues(vertical = Theme.sizes.padding8),
                lazyListState = lazyListState,
            )
        }
    }

    private fun onBankPresetClick(bankPreset: BankPreset) {
        dispatch(OnSelectAction(target.target, bankPreset))
        back()
    }

    @Composable
    private fun BankPresetShimmer() {
        Shimmer {
            Column(
                verticalArrangement = Arrangement.spacedBy(Theme.sizes.padding4)
            ) {
                Spacer(Modifier.height(Theme.sizes.padding4))
                repeat(SHIMMER_ITEM_COUNT) { BankPresetPreview.Shimmer() }
            }
        }
    }

    @Parcelize
    data class Target(
        val target: String,
        val selected: BankPreset?
    ) : NavigationTarget.Page

    @Parcelize
    data class OnSelectAction(
        val target: String,
        val selected: BankPreset?
    ) : PlainAction

    @Parcelize
    data object OnInit : PlainAction

    companion object {
        inline operator fun <reified T> invoke(
            selected: BankPreset? = null,
        ) = Target(T::class.java.name, selected)
    }
}

private const val SHIMMER_ITEM_COUNT = 30
private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3
