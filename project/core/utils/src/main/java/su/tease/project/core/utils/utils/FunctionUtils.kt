package su.tease.project.core.utils.utils

import androidx.compose.runtime.Composable

typealias Callback = () -> Unit
typealias ComposableContent = @Composable () -> Unit

inline fun <T> tryOrDefault(defaultIfError: T, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    defaultIfError
}
