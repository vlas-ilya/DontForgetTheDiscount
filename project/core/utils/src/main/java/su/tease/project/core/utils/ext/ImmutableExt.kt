package su.tease.project.core.utils.ext

import androidx.compose.runtime.Stable
import su.tease.project.core.utils.utils.ImmutableHolder
import su.tease.project.core.utils.utils.ImmutableList
import su.tease.project.core.utils.utils.ImmutableMap
import su.tease.project.core.utils.utils.ImmutableSet

@Stable
fun <T> T.toImmutable(): ImmutableHolder<T> = ImmutableHolder(this)

@Stable
fun <T> List<T>.toImmutable(): ImmutableList<T> = ImmutableList(this)

@Stable
fun <K, V> Map<K, V>.toImmutable(): ImmutableMap<K, V> = ImmutableMap(this)

@Stable
fun <T> Set<T>.toImmutable(): ImmutableSet<T> = ImmutableSet(this)
