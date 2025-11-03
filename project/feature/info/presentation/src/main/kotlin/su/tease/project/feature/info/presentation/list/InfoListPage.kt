package su.tease.project.feature.info.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import su.tease.core.mvi.component.component.container.LocalFeatureConfig
import su.tease.core.mvi.component.component.container.LocalRootConfig
import su.tease.core.mvi.component.component.impl.BasePageComponent
import su.tease.core.mvi.navigation.NavigationTarget
import su.tease.design.theme.api.Theme
import su.tease.project.core.mvi.api.selector.Selector
import su.tease.project.core.mvi.api.store.Store
import su.tease.project.core.mvi.navigation.state.NavigationState
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.page.DFPage
import su.tease.project.design.component.controls.page.DFPageFloatingButton
import su.tease.project.feature.info.presentation.R
import su.tease.project.feature.info.presentation.navigation.NavigationTargets
import su.tease.project.feature.info.presentation.list.component.ListItem
import su.tease.project.feature.info.presentation.list.utils.infoItems
import su.tease.project.design.icons.R as RIcons

class InfoListPage(
    store: Store<*>,
    private val navigationTargets: NavigationTargets,
    private val resourceProvider: ResourceProvider,
) : BasePageComponent<InfoListPage.Target>(store) {

    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    @Composable
    override fun invoke() {

        val infoItems = remember { infoItems(navigationTargets) }
        val isScrollTopButtonVisible = remember {
            derivedStateOf { lazyListState.firstVisibleItemIndex >= SCROLL_ITEMS_FOR_SHOW_BUTTON }
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
                        icon = RIcons.drawable.angle_up,
                        onClick = { scrollUp() },
                        type = DFPageFloatingButton.Type.GRAY,
                        isVisible = isScrollTopButtonVisible.value
                    )
                )
            }
        }

        DFPage(
            title = stringResource(R.string.Info_InfoListPage_Title),
            floatingButtons = floatingButtons.value,
            actionIcon = LocalFeatureConfig.current.action?.icon,
            onActionPress = LocalFeatureConfig.current.action?.onClick,
            hasSystemNavigationBar = LocalRootConfig.current.hasSystemNavigationBar,
            showBackButton = selectAsState(hasPrevPageSelector).value,
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(vertical = Theme.sizes.padding6),
            ) {
                itemsIndexed(
                    items = infoItems,
                    key = { index, it -> it.id },
                ) { index, it ->
                    ListItem(it) { it.navigation.forward() }
                }
            }
        }
    }

    @Parcelize
    data object Target : NavigationTarget.Page
}

private const val SCROLL_ITEMS_FOR_SHOW_BUTTON = 3

val hasPrevPageSelector = Selector<NavigationState, Boolean> {
    val page = root.feature.stack.prev?.value?.page // Prev page in current feature
        ?: root.app.stack.prev?.value?.page // Last page in prev feature
        ?: root.stack.prev?.value?.page // Last page in prev app
    page != null
}
