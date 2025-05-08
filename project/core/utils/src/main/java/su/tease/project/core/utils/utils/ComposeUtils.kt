package su.tease.project.core.utils.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlin.math.absoluteValue

@Composable
fun <T> memoize(
    key: Any = Unit,
    block: suspend CoroutineScope.() -> T
): State<T?> {
    val rememberBlock by rememberUpdatedState(block)
    val state = remember { mutableStateOf<T?>(null) }
    LaunchedEffect(key) {
        state.value = rememberBlock()
    }
    return state
}

@Composable
fun <T> memoize(
    defaultValue: T,
    key: Any = Unit,
    block: suspend CoroutineScope.() -> T
): State<T> {
    val rememberBlock by rememberUpdatedState(block)
    val state = remember { mutableStateOf(defaultValue) }
    LaunchedEffect(key) {
        state.value = rememberBlock()
    }
    return state
}

enum class ScrollDirection {
    TOP, BOTTOM
}

inline fun scrollDirectionState(
    initValue: ScrollDirection = ScrollDirection.BOTTOM,
    dpToPx: (Dp) -> Float
): Triple<State<ScrollDirection>, NestedScrollConnection, () -> Unit> {
    val scrollDirection = mutableStateOf(initValue)

    val threshold = dpToPx(THRESHOLD.dp)
    val scrollDelta = mutableFloatStateOf(0f)

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y < 0 && scrollDirection.value == ScrollDirection.BOTTOM) {
                scrollDelta.floatValue += available.y
                if (scrollDelta.floatValue.absoluteValue > threshold) {
                    scrollDelta.floatValue = 0F
                    scrollDirection.value = ScrollDirection.TOP
                }
            }

            if (available.y > 0 && scrollDirection.value == ScrollDirection.TOP) {
                scrollDelta.floatValue += available.y
                if (scrollDelta.floatValue.absoluteValue > threshold) {
                    scrollDelta.floatValue = 0F
                    scrollDirection.value = ScrollDirection.BOTTOM
                }
            }

            return Offset.Zero
        }
    }

    return Triple(scrollDirection, nestedScrollConnection) {
        scrollDirection.value = ScrollDirection.BOTTOM
    }
}

@Composable
fun rememberScrollDirection(
    initValue: ScrollDirection = ScrollDirection.BOTTOM
): Triple<State<ScrollDirection>, NestedScrollConnection, () -> Unit> {
    return scrollDirectionState(initValue) {
        with(LocalDensity.current) { THRESHOLD.dp.toPx() }
    }
}

@Composable
fun rememberCallback(
    vararg keys: Any?,
    callback: () -> Unit
): () -> Unit = remember(*keys) { callback }

@PublishedApi
internal const val THRESHOLD = 50
