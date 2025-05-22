package su.tease.project.core.utils.ext

import androidx.collection.MutableObjectList
import androidx.collection.ObjectList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

inline fun <T, R> ObjectList<T>.map(transform: (T) -> R): ObjectList<R> =
    MutableObjectList<R>(size).apply { this@map.forEach { add(transform(it)) } }

inline fun <T> ObjectList<T>.filter(predicate: (T) -> Boolean): ObjectList<T> =
    MutableObjectList<T>(size).apply { this@filter.forEach { if (predicate(it)) add(it) } }

inline fun <T, R> ObjectList<T>.flatMap(transform: (T) -> ObjectList<R>): ObjectList<R> =
    MutableObjectList<R>(size).apply { this@flatMap.forEach { addAll(transform(it)) } }

fun <T> ObjectList<ObjectList<T>>.flatten(): ObjectList<T> =
    MutableObjectList<T>(size).apply { this@flatten.forEach { addAll(it) } }

fun <T> PersistentList<T>.toObjectList(): ObjectList<T> =
    MutableObjectList<T>(size).apply { addAll(this@toObjectList) }

fun <T> ObjectList<T>.toPersistentList(): PersistentList<T> =
    this.asList().toPersistentList()
