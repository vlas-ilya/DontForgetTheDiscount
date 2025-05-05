package su.tease.project.core.utils.resource

import androidx.compose.ui.unit.Dp

interface ResourceProvider {
    fun dpToPx(dp: Dp): Float
}
