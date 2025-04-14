package su.tease.project.core.utils.stack

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stack<T : Parcelable>(
    val prev: Stack<T>?,
    val value: T,
) : Parcelable
