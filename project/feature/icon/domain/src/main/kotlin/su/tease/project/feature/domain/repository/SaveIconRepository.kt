package su.tease.project.feature.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import su.tease.project.feature.domain.usecase.FilePath

interface SaveIconRepository {
    suspend fun read(uri: Uri): Bitmap?
    suspend fun save(fileName: String, bitmap: Bitmap): FilePath?
}
