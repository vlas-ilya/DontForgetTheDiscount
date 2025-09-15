package su.tease.project.feature.cashback.data.repository

import su.tease.project.feature.cashback.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.cashback.domain.repository.PresetRepository

class PresetRepositoryImpl(
    private val presetIntegrationInteractor: PresetIntegrationInteractor,
) : PresetRepository {

    override suspend fun get(cashBackPresetId: String) =
        presetIntegrationInteractor.get(cashBackPresetId)
}
