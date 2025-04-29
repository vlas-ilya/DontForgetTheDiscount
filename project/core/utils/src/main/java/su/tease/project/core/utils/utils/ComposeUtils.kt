package su.tease.project.core.utils.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope

@Composable
inline fun <T> memoize(
    key: Any = Unit,
    crossinline block: suspend CoroutineScope.() -> T
): State<T?> {
    val state = remember { mutableStateOf<T?>(null) }
    LaunchedEffect(key) {
        state.value = block()
    }
    return state
}

@Composable
inline fun <T> memoize(
    defaultValue: T,
    key: Any = Unit,
    crossinline block: suspend CoroutineScope.() -> T
): State<T> {
    val state = remember { mutableStateOf(defaultValue) }
    LaunchedEffect(key) {
        state.value = block()
    }
    return state
}
