package su.tease.project.core.utils.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.CoroutineScope

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
