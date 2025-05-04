@file:Suppress("SwallowedException", "TooGenericExceptionCaught")

package su.tease.project.core.utils.utils

inline fun <T> tryOrDefault(returnOnError: T, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    returnOnError
}

inline fun <T> tryOrDefault(returnOnError: () -> T, block: () -> T) = try {
    block()
} catch (e: Throwable) {
    returnOnError()
}
