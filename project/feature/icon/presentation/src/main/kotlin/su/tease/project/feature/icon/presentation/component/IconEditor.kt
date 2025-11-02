package su.tease.project.feature.icon.presentation.component

import android.net.Uri
import androidx.collection.IntIntPair
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun IconEditor(
    uri: Uri?,
    onScaleChange: (Float) -> Unit,
    onOffsetChange: (Offset) -> Unit,
    onRotationChange: (Float) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Color.LightGray, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val parentSizePx = with(LocalDensity.current) { maxWidth.toPx() }
            val imageBoxSize = remember { mutableFloatStateOf(0f) }

            val scale = remember {
                derivedStateOf {
                    if (imageBoxSize.floatValue == 0f) return@derivedStateOf 1f
                    if (parentSizePx > imageBoxSize.floatValue) return@derivedStateOf parentSizePx / imageBoxSize.floatValue
                    1f
                }
            }

            val offset = remember {
                derivedStateOf {
                    IntIntPair(
                        ((parentSizePx - imageBoxSize.floatValue) / 2).roundToInt(),
                        ((parentSizePx - imageBoxSize.floatValue) / 2).roundToInt(),
                    )
                }
            }

            uri?.let {
                TransformableImage(
                    imageUri = it,
                    onScaleChange = onScaleChange,
                    onOffsetChange = onOffsetChange,
                    onRotationChange = onRotationChange,
                    modifier = Modifier
                        .onSizeChanged { size ->
                            imageBoxSize.floatValue = minOf(size.height, size.width).toFloat()
                        }
                        .offset { IntOffset(offset.value.first, offset.value.second) }
                        .scale(scale.value)
                )
            }
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .border(2.dp, Color.White, CircleShape)
        )
    }
}

