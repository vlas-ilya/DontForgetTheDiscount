package su.tease.project.feature.cacheback.domain.repository

interface SyncPresetRepository {
    suspend fun sync()
}
