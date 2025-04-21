package su.tease.project.core.utils.either

sealed interface Either<L, R> {
    data class Left<L, R>(
        val left: L
    ) : Either<L, R>

    data class Right<L, R>(
        val right: R
    ) : Either<L, R>
}
