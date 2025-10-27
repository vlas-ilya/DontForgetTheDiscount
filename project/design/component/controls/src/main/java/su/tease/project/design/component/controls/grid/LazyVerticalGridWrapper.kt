package su.tease.project.design.component.controls.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import kotlinx.coroutines.launch
import su.tease.project.core.utils.resource.ResourceProvider
import su.tease.project.core.utils.utils.ScrollDirection
import su.tease.project.core.utils.utils.rememberCallback
import su.tease.project.core.utils.utils.scrollDirectionState
import su.tease.project.design.component.controls.shimmer.Shimmer as DsShimmer

class LazyVerticalGridWrapper(
    private val resourceProvider: ResourceProvider,
    val scrollItemsForShowButton: Int,
) {

    private val lazyGridState = LazyGridState(0, 0)
    private val scrollDirectionState = scrollDirectionState { resourceProvider.dpToPx(it) }

    val scrollState: ScrollState
        @Composable
        get() {
            val isScrollTopButtonVisible = remember {
                derivedStateOf {
                    lazyGridState.firstVisibleItemIndex >= scrollItemsForShowButton
                }
            }
            val scope = rememberCoroutineScope()
            val (scrollDirection, nestedScrollConnection, resetScroll) = scrollDirectionState
            val scrollUp = rememberCallback(resetScroll, lazyGridState) {
                scope.launch {
                    resetScroll()
                    lazyGridState.animateScrollToItem(0)
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
        columns: GridCells,
        contentPadding: PaddingValues,
        verticalArrangement: Arrangement.Vertical,
        horizontalArrangement: Arrangement.Horizontal,
        content: LazyGridScope.() -> Unit,
    ) {
        LazyVerticalGrid(
            state = lazyGridState,
            columns = columns,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
        ) {
            content()
        }
    }

    @Composable
    fun Shimmer(
        columns: GridCells,
        contentPadding: PaddingValues,
        verticalArrangement: Arrangement.Vertical,
        horizontalArrangement: Arrangement.Horizontal,
        content: LazyGridScope.() -> Unit,
    ) {
        DsShimmer {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = columns,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement,
                userScrollEnabled = false,
            ) {
                content()
            }
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