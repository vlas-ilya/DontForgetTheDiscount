package su.tease.design.theme.api.utils

import androidx.compose.runtime.compositionLocalOf
import su.tease.design.theme.api.Theme

val LocalTheme = compositionLocalOf<Theme> { error("No default theme") }
