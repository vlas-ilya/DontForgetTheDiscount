package su.tease.project.core.utils.ext

import kotlinx.collections.immutable.PersistentList

fun <T> PersistentList<T>.removeAll(value: T) = removeAll { it == value }

fun <T> PersistentList<T>.removeLast() = removeAt(size - 1)

fun <T> PersistentList<T>.inLastN(item: T, n: Int): Boolean {
    return size - indexOf(item) <= n
}

fun <T> PersistentList<T>.inFirstN(item: T, n: Int): Boolean {
    return indexOf(item) < n
}
