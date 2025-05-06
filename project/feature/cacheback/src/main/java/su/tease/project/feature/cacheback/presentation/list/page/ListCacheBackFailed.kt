@file:Suppress("EmptyFunctionBlock", "UnusedParameter")

package su.tease.project.feature.cacheback.presentation.list.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
fun ListCacheBackFailed(
    error: State<Boolean>,
    onTryAgain: () -> Unit,
) {
}
