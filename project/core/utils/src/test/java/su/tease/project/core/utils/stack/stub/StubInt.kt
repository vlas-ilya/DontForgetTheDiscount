package su.tease.project.core.utils.stack.stub

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class StubInt(val value: Int) : Parcelable

fun List<Int>.toStubIntList(): List<StubInt> = map(::StubInt)
