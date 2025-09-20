package su.tease.project.feature.bank.data.dependencies

import su.tease.project.feature.bank.domain.entity.BankPreset

interface PresetIntegrationInteractor {
    suspend fun get(presetId: String): BankPreset
    suspend fun list(presetIds: List<String>): List<BankPreset>
}
