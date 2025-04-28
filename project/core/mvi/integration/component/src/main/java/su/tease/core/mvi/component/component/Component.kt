package su.tease.core.mvi.component.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
abstract class Component {

    @Composable
    abstract operator fun invoke()
}
