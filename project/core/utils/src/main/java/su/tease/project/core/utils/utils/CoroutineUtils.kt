package su.tease.project.core.utils.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T> withIO(crossinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO) { block() }

suspend inline fun <T> withMain(crossinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main) { block() }

suspend inline fun <T> withMainImmediate(crossinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main.immediate) { block() }

suspend inline fun <T> withDefault(crossinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default) { block() }

suspend inline fun <T> withUnconfined(crossinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Unconfined) { block() }
