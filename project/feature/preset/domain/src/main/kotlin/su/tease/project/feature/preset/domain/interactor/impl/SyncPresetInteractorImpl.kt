package su.tease.project.feature.preset.domain.interactor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.domain.interactor.SyncPresetInteractor
import su.tease.project.feature.preset.domain.repository.SyncPresetRepository

class SyncPresetInteractorImpl(
    private val syncPresetRepository: SyncPresetRepository
) : SyncPresetInteractor {
    override suspend fun sync() = withDefault {
        syncPresetRepository.sync()
    }
}
