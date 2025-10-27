package su.tease.project.design.component.controls.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.shimmer.Shimmer as DsShimmer

class LazyListWrapper(
    resourceProvider: ResourceProvider,
    val scrollItemsForShowButton: Int,
) {
    private val lazyListState = LazyListState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    val scrollState: ScrollState
        @Composable
        get() {
            val isScrollTopButtonVisible = remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex >= scrollItemsForShowButton
                }
            }
            val scope = rememberCoroutineScope()
            val (scrollDirection, nestedScrollConnection, resetScroll) = scrollDirectionState
            val scrollUp = rememberCallback(resetScroll, lazyListState) {
                scope.launch {
                    resetScroll()
                    lazyListState.animateScrollToItem(0)
                }
            }
            return ScrollState(
                isScrollTopButtonVisible = isScrollTopButtonVisible,
                scrollDirection = scrollDirection,
                nestedScrollConnection = nestedScrollConnection,
                resetScroll = resetScroll,
                scrollUp = scrollUp,
            )
        }

    @Composable
    fun Compose(
        count: Int,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        itemContent: (index: Int) -> LazyListItem,
    ) {
        LazyList(
            count = count,
            modifier = modifier,
            itemContent = itemContent,
            verticalArrangement = verticalArrangement,
            contentPadding = contentPadding,
            lazyListState = lazyListState,
        )
    }

    @Composable
    fun Shimmer(
        count: Int,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        itemContent: (index: Int) -> LazyListItem,
    ) {
        DsShimmer {
            LazyList(
                count = count,
                modifier = modifier,
                itemContent = itemContent,
                verticalArrangement = verticalArrangement,
                contentPadding = contentPadding,
                lazyListState = rememberLazyListState(),
                userScrollEnabled = false,
            )
        }
    }

    @Immutable
    data class ScrollState(
        val isScrollTopButtonVisible: State<Boolean>,
        val scrollDirection: State<ScrollDirection>,
        val nestedScrollConnection: NestedScrollConnection,
        val resetScroll: () -> Unit,
        val scrollUp: () -> Unit,
    )
}