package su.tease.design.component.navigation_bar.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter

@Immutable
data class NavigationBarItemData<T>(
    val value: T,
    val name: String,
    val image: Painter,
    val contentDescription: String = name,
)
