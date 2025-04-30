package su.tease.project.design.theme.impl.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import su.tease.design.theme.api.Theme
import su.tease.project.design.theme.impl.Theme

@Suppress("ModifierMissing")
@Composable
fun Preview(content: @Composable ColumnScope.() -> Unit) {
    Theme {
        Column(
            modifier = Modifier
                .background(Theme.colors.background1)
                .fillMaxSize()
        ) {
            content()
        }
    }
}
