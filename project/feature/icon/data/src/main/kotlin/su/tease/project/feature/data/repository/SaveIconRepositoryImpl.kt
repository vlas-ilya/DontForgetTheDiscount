package su.tease.project.feature.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import su.tease.project.core.utils.utils.withIO
import su.tease.project.feature.domain.repository.SaveIconRepository
import su.tease.project.feature.domain.usecase.FilePath
import java.io.File
import java.io.FileOutputStream

class SaveIconRepositoryImpl(
    private val context: Context
) : SaveIconRepository {

    override suspend fun read(uri: Uri): Bitmap? = withIO {
        context.contentResolver
            .openInputStream(uri)
            ?.use { stream -> BitmapFactory.decodeStream(stream) }
    }

    override suspend fun save(
        fileName: String,
        bitmap: Bitmap
    ): FilePath? = withIO {
        try {
            val iconsDir = File(context.filesDir, ICONS_DIRECTORY)
            if (!iconsDir.exists()) iconsDir.mkdirs()
            val file = File(iconsDir, fileName)
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }
            Uri.fromFile(file).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val ICONS_DIRECTORY = "icons"
    }
}