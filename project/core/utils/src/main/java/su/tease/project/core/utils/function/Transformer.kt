package su.tease.project.core.utils.function

fun interface Transformer<T> {
    operator fun invoke(value: T): T
}
