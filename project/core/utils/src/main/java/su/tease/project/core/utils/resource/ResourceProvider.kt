package su.tease.project.core.utils.resource

import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp

interface ResourceProvider {
    fun dpToPx(dp: Dp): Float
    fun string(@StringRes id: Int): String
}
