package su.tease.project.feature.icon.presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
        uri?.let {
            TransformableImage(
                imageUri = it,
                onScaleChange = onScaleChange,
                onOffsetChange = onOffsetChange,
                onRotationChange = onRotationChange,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .border(2.dp, Color.White, CircleShape)
        )
    }
}

