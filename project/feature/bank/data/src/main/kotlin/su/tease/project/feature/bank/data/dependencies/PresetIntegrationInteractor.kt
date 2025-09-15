package su.tease.project.feature.bank.data.dependencies

import su.tease.project.feature.bank.domain.entity.BankPreset

interface PresetIntegrationInteractor {
    suspend fun get(bankPresetId: String): BankPreset
    suspend fun list(bankPresetIds: List<String>): List<BankPreset>
}
