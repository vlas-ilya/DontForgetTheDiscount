package su.tease.project.design.component.controls.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun DFImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    GlideImage(
        model = url,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}
