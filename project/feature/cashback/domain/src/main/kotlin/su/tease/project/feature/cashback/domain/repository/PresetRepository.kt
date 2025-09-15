package su.tease.project.feature.cashback.domain.repository

import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

interface PresetRepository {
    suspend fun get(cashBackPresetId: String): CashBackPreset
}
