package su.tease.project.feature.domain.usecase

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.createBitmap
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.core.utils.uuid.UuidProvider
import su.tease.project.feature.domain.repository.SaveIconRepository

class SaveIconUseCase(
    private val repository: SaveIconRepository,
    private val uuidProvider: UuidProvider,
) {

    suspend fun saveIcon(
        uri: Uri,
        scale: Float,
        offset: Offset,
        rotation: Float,
    ): FilePath = withDefault {
        val originalBitmap = repository.read(uri) ?: throw SaveIconException()
        val transformedBitmap = applyTransformations(originalBitmap, scale, offset, rotation)
        val fileName = "icon_${uuidProvider.uuid()}.png"
        repository.save(fileName, transformedBitmap) ?: throw SaveIconException()
    }

    private suspend fun applyTransformations(
        bitmap: Bitmap,
        scale: Float,
        offset: Offset,
        rotation: Float
    ): Bitmap = withDefault {
        val minSide = minOf(bitmap.width, bitmap.height)
        createBitmap(minSide, minSide).apply {
            val canvas = Canvas(this)
            val matrix = Matrix().apply {
                val cx = bitmap.width / 2f
                val cy = bitmap.height / 2f

                val targetCx = minSide / 2f
                val targetCy = minSide / 2f

                postTranslate(targetCx - cx, targetCy - cy)

                postScale(scale, scale, targetCx, targetCy)
                postRotate(rotation, targetCx, targetCy)

                postTranslate(offset.x, offset.y)
            }
            canvas.drawBitmap(bitmap, matrix, null)
        }
    }
}

class SaveIconException : Throwable()

typealias FilePath = String
