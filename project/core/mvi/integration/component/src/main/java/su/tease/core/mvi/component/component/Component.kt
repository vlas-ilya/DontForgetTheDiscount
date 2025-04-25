package su.tease.core.mvi.component.component

import androidx.compose.runtime.Composable

abstract class Component {

    @Composable
    abstract operator fun invoke()
}
