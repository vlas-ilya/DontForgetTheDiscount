package su.tease.project.core.utils.ext

import androidx.compose.ui.Modifier

fun Modifier.thenIf(condition: Boolean, modifier: Modifier): Modifier {
    return if (condition) {
        then(modifier)
    } else {
        this
    }
}

fun Modifier.thenIf(condition: Boolean, modify: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        modify(this)
    } else {
        this
    }
}
