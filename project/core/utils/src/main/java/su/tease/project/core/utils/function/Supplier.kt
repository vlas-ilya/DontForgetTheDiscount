package su.tease.project.core.utils.function

fun interface Supplier<T> {
    operator fun invoke(): T
}
