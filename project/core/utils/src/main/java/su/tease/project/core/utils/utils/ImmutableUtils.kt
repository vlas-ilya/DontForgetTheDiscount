package su.tease.project.core.utils.utils

import androidx.compose.runtime.Immutable

@JvmInline
@Immutable
value class ImmutableSet<T>(private val set: Set<T>) : Set<T> by set

@JvmInline
@Immutable
value class ImmutableMap<K, V>(private val map: Map<K, V>) : Map<K, V> by map

@JvmInline
@Immutable
value class ImmutableList<T>(private val list: List<T>) : List<T> by list

@JvmInline
@Immutable
value class ImmutableHolder<T>(val item: T)

fun <T> immutableListOf(vararg element: T) = ImmutableList(element.toList())
