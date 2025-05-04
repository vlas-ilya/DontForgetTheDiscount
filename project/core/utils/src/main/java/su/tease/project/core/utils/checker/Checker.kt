package su.tease.project.core.utils.checker

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

inline fun <T, Error> check(
    value: State<T>,
    crossinline checker: CheckContext<Error>.(T) -> Unit,
): State<Error?> =
    derivedStateOf {
        val context = CheckContextImpl<Error>()
        try {
            checker(context, value.value)
            null
        } catch (_: CheckContextException) {
            context.error
        }
    }

interface CheckContext<Error> {
    fun error(error: Error)
    fun require(condition: Boolean, error: Error)
}

class CheckContextException : Exception()

@PublishedApi
internal class CheckContextImpl<Error> : CheckContext<Error> {
    var error: Error? = null
    override fun error(error: Error) {
        this.error = error
        throw CheckContextException()
    }

    override fun require(condition: Boolean, error: Error) {
        if (condition) error(error)
    }
}
