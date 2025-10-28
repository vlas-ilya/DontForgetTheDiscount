package su.tease.project.feature.icon.presentation.component

import android.net.Uri
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun TransformableImage(
    imageUri: Uri,
    onScaleChange: (Float) -> Unit,
    onOffsetChange: (Offset) -> Unit,
    onRotationChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentScale = remember { mutableFloatStateOf(1f) }
    val currentOffset = remember { mutableStateOf(Offset.Zero) }
    val currentRotation = remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { centroid, pan, gestureScale, rotate ->
                        currentScale.floatValue = (currentScale.floatValue * gestureScale).coerceIn(0.5f, 3f)
                        currentOffset.value += pan
                        currentRotation.floatValue += rotate
                        onScaleChange(currentScale.floatValue)
                        onOffsetChange(currentOffset.value)
                        onRotationChange(currentRotation.floatValue)
                    }
                )
            }
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "",
            modifier = Modifier
                .graphicsLayer {
                    scaleX = currentScale.floatValue
                    scaleY = currentScale.floatValue
                    translationX = currentOffset.value.x
                    translationY = currentOffset.value.y
                    rotationZ = currentRotation.floatValue
                }
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}