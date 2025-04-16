package su.tease.project.core.utils.stack

import android.os.Parcelable

inline fun <reified T : Parcelable> List<T>.toStack(): Stack<T>? = this
    .takeIf { it.isNotEmpty() }
    ?.let { makeStack(first(), drop(1)) }

fun <T : Parcelable> makeStack(
    first: T,
    vararg other: T,
): Stack<T> = other.fold(Stack(prev = null, value = first)) { acc, item ->
    Stack(prev = acc, value = item)
}

fun <T : Parcelable> makeStack(
    first: T,
    other: List<T>,
): Stack<T> = other.fold(Stack(prev = null, value = first)) { acc, item ->
    Stack(prev = acc, value = item)
}
