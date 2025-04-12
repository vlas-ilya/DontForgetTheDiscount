package su.tease.project.design.theme.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import su.tease.design.theme.api.utils.LocalTheme
import su.tease.project.design.theme.impl.utils.ThemeImpl

private val currentThemeValue = mutableStateOf(ThemeValue.LIGHT)

@Composable
fun Theme(block: @Composable () -> Unit) {
    val theme = remember(currentThemeValue.value) { ThemeImpl.make(currentThemeValue.value) }
    CompositionLocalProvider(LocalTheme provides theme) {
        block()
    }
}

fun switchTheme(theme: ThemeValue) {
    currentThemeValue.value = theme
}
