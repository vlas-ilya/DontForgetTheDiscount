package su.tease.project.core.utils.ext

fun <T> List<T>.halves(): Pair<List<T>, List<T>> = (size / 2)
    .let { take(it) to drop(it) }
