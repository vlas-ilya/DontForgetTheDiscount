package su.tease.project.feature.preset.impl.domain.repository

interface SyncPresetRepository {
    suspend fun sync()
}
