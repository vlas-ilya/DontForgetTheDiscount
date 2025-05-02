package su.tease.project.core.utils.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun <T, R> State<T>.map(transform: (T) -> R): State<R> = remember(this) {
    derivedStateOf { transform(value) }
}

@Composable
fun <T> RedirectState(from: State<T>, to: MutableState<T>) {
    LaunchedEffect(from.value, to) {
        to.value = from.value
    }
}
