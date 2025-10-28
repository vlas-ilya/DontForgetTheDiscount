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
        val circularBitmap = applyCircularMask(transformedBitmap)
        val fileName = "icon_${uuidProvider.uuid()}.png"
        repository.save(fileName, circularBitmap) ?: throw SaveIconException()
    }

    private suspend fun applyTransformations(
        bitmap: Bitmap,
        scale: Float,
        offset: Offset,
        rotation: Float
    ): Bitmap = withDefault {
        val matrix = Matrix().apply {
            postScale(scale, scale)
            postTranslate(offset.x, offset.y)
            postRotate(rotation)
        }
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private suspend fun applyCircularMask(bitmap: Bitmap): Bitmap = withDefault {
        val output = createBitmap(bitmap.width, bitmap.height)
        val canvas = Canvas(output)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        val radius = minOf(bitmap.width, bitmap.height) / 2f

        val path = Path().apply {
            addCircle(bitmap.width / 2f, bitmap.height / 2f, radius, Path.Direction.CW)
        }

        canvas.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        output
    }
}

class SaveIconException : Throwable()

typealias FilePath = String
