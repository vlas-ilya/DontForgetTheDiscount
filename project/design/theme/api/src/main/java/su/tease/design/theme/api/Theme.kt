package su.tease.design.theme.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import su.tease.design.theme.api.utils.LocalTheme

@Immutable
interface Theme {
    val color: Colors
    val font: Fonts
    val size: Sizes

    companion object {
        val colors: Colors
            @Composable
            get() = LocalTheme.current.color

        val fonts: Fonts
            @Composable
            get() = LocalTheme.current.font

        val sizes: Sizes
            @Composable
            get() = LocalTheme.current.size
    }
}


