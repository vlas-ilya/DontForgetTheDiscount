package su.tease.project.feature.cashback.data.dependencies

import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

interface PresetIntegrationInteractor {
    suspend fun get(cashBackPresetId: String): CashBackPreset
    suspend fun list(presetIds: List<String>): List<CashBackPreset>
}
