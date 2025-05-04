package su.tease.project.core.utils.utils

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

inline fun <E> buildPersistentList(builderAction: MutableList<E>.() -> Unit): PersistentList<E> =
    buildList(builderAction).toPersistentList()
