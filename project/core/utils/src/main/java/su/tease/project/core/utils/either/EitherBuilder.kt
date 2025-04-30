@file:Suppress("SwallowedException")

package su.tease.project.core.utils.either

import su.tease.project.core.utils.either.Either.Left
import su.tease.project.core.utils.either.Either.Right

class EitherContextException : Throwable("")

class EitherContext<L> {
    var left: L? = null

    fun <R> Either<L, R>.right(): R = when (this) {
        is Left -> {
            this@EitherContext.left = left
            throw EitherContextException()
        }

        is Right -> right
    }
}

inline fun <L, R> either(block: EitherContext<L>.() -> R): Either<L, R> =
    with(EitherContext<L>()) {
        try {
            block().right()
        } catch (e: EitherContextException) {
            left!!.left()
        }
    }
