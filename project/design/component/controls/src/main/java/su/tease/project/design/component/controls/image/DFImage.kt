package su.tease.project.design.component.controls.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
@Suppress("IMPLICIT_CAST_TO_ANY")
fun DFImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    val model = if (url.endsWith(".svg")) {
        ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build()
    } else {
        url
    }
    AsyncImage(
        model = model,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}
