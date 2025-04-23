package su.tease.project.core.utils.ext

import su.tease.project.core.utils.either.Either
import su.tease.project.core.utils.either.left
import su.tease.project.core.utils.either.right

fun <D, E> Result<D>.toEither(errorMapper: (Throwable) -> E): Either<E, D> =
    when {
        this.isSuccess -> this.getOrNull()!!.right()
        this.isFailure -> errorMapper(this.exceptionOrNull()!!).left()
        else -> error("Impossible branch")
    }
