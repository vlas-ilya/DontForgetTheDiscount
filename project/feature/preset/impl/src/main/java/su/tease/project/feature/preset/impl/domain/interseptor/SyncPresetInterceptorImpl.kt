package su.tease.project.feature.preset.impl.domain.interseptor

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.api.domain.interceptor.SyncPresetInterceptor
import su.tease.project.feature.preset.impl.domain.repository.SyncPresetRepository

class SyncPresetInterceptorImpl(
    private val syncPresetRepository: SyncPresetRepository
): SyncPresetInterceptor {
    override suspend fun sync() = withDefault {
        syncPresetRepository.sync()
    }
}