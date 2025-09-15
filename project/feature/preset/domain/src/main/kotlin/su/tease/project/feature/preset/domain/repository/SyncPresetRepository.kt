package su.tease.project.feature.preset.domain.repository

interface SyncPresetRepository {
    suspend fun sync()
}
