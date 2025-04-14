package su.tease.project.core.utils.stack

import android.os.Parcelable

fun <T : Parcelable> Stack<T>.add(value: T): Stack<T> =
    Stack(prev = this, value = value)

fun <T : Parcelable> Stack<T>.moveToUp(
    value: T,
    compare: (T, T) -> Boolean = { o1, o2 -> o1 == o2 },
): Stack<T> =
    Stack(
        prev = removeAll { compare(it, value) },
        value = value
    )

fun <T : Parcelable> Stack<T>.removeLast(): Stack<T>? = prev

fun <T : Parcelable> Stack<T>.removeAll(predicate: (T) -> Boolean): Stack<T>? =
    prev?.removeAll(predicate)
        .let { if (predicate(value)) it else Stack(prev = it, value = value) }

fun <T : Parcelable> Stack<T>.replaceLast(to: T?): Stack<T>? =
    if (to == null) removeLast()
    else Stack(prev = prev, value = to)

fun <T : Parcelable> Stack<T>.dropLastWhile(predicate: (T) -> Boolean): Stack<T>? =
    if (predicate(value)) prev?.dropLastWhile(predicate)
    else this

fun <T : Parcelable> Stack<T>.last(predicate: (T) -> Boolean): T? =
    if (predicate(value)) value
    else prev?.last(predicate)
