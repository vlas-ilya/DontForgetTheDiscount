package su.tease.project.core.utils.ext

import kotlinx.collections.immutable.PersistentList

fun <T> PersistentList<T>.removeAll(value: T) =
    removeAll { it == value }

fun <T> PersistentList<T>.removeLast() =
    removeAt(size - 1)
