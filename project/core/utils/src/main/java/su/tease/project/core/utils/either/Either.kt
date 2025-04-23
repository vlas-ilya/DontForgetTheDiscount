package su.tease.project.core.utils.either

import su.tease.project.core.utils.either.Either.Left
import su.tease.project.core.utils.either.Either.Right

sealed interface Either<out L, out R> {
    data class Left<out L>(val left: L) : Either<L, Nothing>
    data class Right<out R>(val right: R) : Either<Nothing, R>

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>
}

fun <L, R, T> Either<L, R>.fold(
    fnLeft: (L) -> T?,
    fnRight: (R) -> T?
): T? = when (this) {
    is Left -> fnLeft(left)
    is Right -> fnRight(right)
}

fun <L> L.left(): Either<L, Nothing> = Left(this)

fun <R> R.right(): Either<Nothing, R> = Right(this)

inline fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    apply { if (this is Left) fn(left) }

inline fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    apply { if (this is Right) fn(right) }

inline fun <T, L, R> Either<L, R>.map(mapper: (R) -> (T)): Either<L, T> =
    when (this) {
        is Left -> left.left()
        is Right -> mapper(right).right()
    }

inline fun <T, L, R> Either<L, R>.leftMap(mapper: (L) -> (T)): Either<T, R> =
    when (this) {
        is Left -> mapper(left).left()
        is Right -> right.right()
    }

inline fun <T, L, R> Either<L, R>.flatMap(mapper: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Left -> Left(left)
        is Right -> mapper(right)
    }

inline fun <T, L, R> Either<L, R>.leftFlatMap(mapper: (L) -> Either<T, R>): Either<T, R> =
    when (this) {
        is Left -> mapper(left)
        is Right -> Right(right)
    }

inline fun <A, B, C, D> Either<A, B>.zip(fb: Either<A, C>, f: (B, C) -> D): Either<A, D> =
    flatMap { b -> fb.map { c -> f(b, c) } }

fun <L, R> Either<L, R>.orElse(defaultValue: R): R =
    when (this) {
        is Left -> defaultValue
        is Right -> right
    }

inline fun <L, R> Either<L, R>.orElse(defaultValueProvider: () -> R): R =
    when (this) {
        is Left -> defaultValueProvider.invoke()
        is Right -> right
    }
