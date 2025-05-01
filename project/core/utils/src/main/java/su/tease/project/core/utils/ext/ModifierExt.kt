package su.tease.project.core.utils.ext

import androidx.compose.ui.Modifier

fun Modifier.thenIf(
    condition: Boolean,
    modifier: Modifier,
): Modifier {
    return if (condition) {
        then(modifier)
    } else {
        this
    }
}

inline fun Modifier.thenIf(
    condition: Boolean,
    modify: () -> Modifier,
): Modifier {
    return if (condition) {
        then(modify())
    } else {
        this
    }
}

inline fun <T> Modifier.thenIfNotNull(value: T?, modify: (T) -> Modifier): Modifier {
    return if (value != null) {
        then(modify(value))
    } else {
        this
    }
}

inline fun <reified T> Modifier.thenIfIsInstance(value: Any?, modify: (T) -> Modifier): Modifier {
    return if (value is T) {
        then(modify(value))
    } else {
        this
    }
}
