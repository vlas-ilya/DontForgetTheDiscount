package su.tease.project.design.component.controls.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.valentinilk.shimmer.shimmer

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    block: @Composable () -> Unit
) {
    Box(
        modifier = modifier.shimmer()
    ) {
        block()
    }
}
