package su.tease.project.design.component.controls.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun DFImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    AsyncImage(
        model = url,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}
