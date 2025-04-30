@file:Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException")

package su.tease.project.core.utils.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

fun <T> CoroutineScope.async(
    returnOnError: T,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    launch(context, start) {
        val value = try {
            block()
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            returnOnError
        }
        deferred.complete(value)
    }

    return deferred
}

fun <T> CoroutineScope.async(
    returnOnError: () -> T,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    launch(context, start) {
        val value = try {
            block()
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            returnOnError()
        }
        deferred.complete(value)
    }

    return deferred
}
