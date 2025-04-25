package su.tease.project.core.utils.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun <T, R> State<T>.map(transform: (T) -> R): State<R> = remember(this) {
    derivedStateOf { transform(value) }
}
