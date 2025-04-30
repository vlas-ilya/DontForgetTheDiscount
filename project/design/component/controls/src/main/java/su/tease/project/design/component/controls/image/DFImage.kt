package su.tease.project.design.component.controls.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
@Suppress("IMPLICIT_CAST_TO_ANY", "ComposableParamOrder")
fun DFImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String,
    tint: Color? = null,
) {
    val model = if (url.endsWith(".svg")) {
        ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(SvgDecoder.Factory())
            .build()
    } else {
        url
    }
    SubcomposeAsyncImage(
        model = model,
        modifier = modifier,
        contentDescription = contentDescription,
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                // TODO Error content
            }
            is AsyncImagePainter.State.Loading -> {
                // TODO Loader content
            }
            else -> Image(
                painter = painter,
                contentDescription = contentDescription,
                colorFilter = tint?.let { ColorFilter.tint(it) }
            )
        }
    }
}
