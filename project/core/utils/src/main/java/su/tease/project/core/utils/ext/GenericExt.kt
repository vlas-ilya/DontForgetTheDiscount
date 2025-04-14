package su.tease.project.core.utils.ext

inline fun <T> T.transformIf(condition: Boolean, block: (T) -> T): T =
    if (condition) block(this) else this

inline fun <T> T.tryTransformIf(condition: Boolean, block: (T) -> T?): T? =
    if (condition) block(this) else this


inline fun <T, R> T.letIf(condition: Boolean, block: (T) -> R): R? =
    if (condition) block(this) else null

fun <T> T.unit(): Unit = Unit