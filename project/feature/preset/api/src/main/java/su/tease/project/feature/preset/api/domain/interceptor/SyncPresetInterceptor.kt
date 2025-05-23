package su.tease.project.feature.preset.api.domain.interceptor

interface SyncPresetInterceptor {
    suspend fun sync()
}