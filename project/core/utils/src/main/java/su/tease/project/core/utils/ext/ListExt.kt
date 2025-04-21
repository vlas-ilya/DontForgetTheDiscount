package su.tease.project.core.utils.ext

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

fun <T> List<T>.halves(): Pair<List<T>, List<T>> = (size / 2)
    .let { take(it) to drop(it) }

inline fun <T, R> Iterable<T>.mapPersistent(transform: (T) -> R): PersistentList<R> =
    map(transform).toPersistentList()
