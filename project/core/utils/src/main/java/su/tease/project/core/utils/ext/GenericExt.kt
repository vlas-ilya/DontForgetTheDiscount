package su.tease.project.core.utils.ext

inline fun <T> T.transformIf(
    condition: Boolean,
    block: (T) -> T,
): T = if (condition) block(this) else this

inline fun <T> T.transformIfNullable(
    condition: Boolean,
    block: (T) -> T?,
): T? = if (condition) block(this) else this

inline fun <T> T.transformIfOrSelf(
    condition: Boolean,
    block: (T) -> T?,
): T = if (condition) (block(this) ?: this) else this

inline fun <T, R> T.letIf(
    condition: Boolean,
    block: (T) -> R,
): R? = if (condition) block(this) else null

inline fun <T, R> T.runIf(
    condition: Boolean,
    block: T.() -> R,
): R? = if (condition) block(this) else null

inline fun <R> runIf(
    condition: Boolean,
    block: () -> R,
): R? = if (condition) block() else null

inline fun <T> T.alsoIf(
    condition: Boolean,
    block: (T) -> Unit,
): T = also {
    if (condition) block(it)
}

inline fun <T> T.applyIf(
    condition: Boolean,
    block: T.() -> Unit,
): T = apply {
    if (condition) block(this)
}

inline fun <T, R> via(receiver: T, block: (T) -> R): R =
    block(receiver)

fun Any?.unit(): Unit = Unit

fun <T> Boolean.choose(
    then: T,
    other: T,
): T = if (this) then else other

inline fun <T> Boolean.choose(
    then: () -> T,
    other: () -> T,
): T = if (this) then() else other()

inline fun <reified T> Any?.takeIfIsInstance(): T? {
    return if (this is T) this else null
}

