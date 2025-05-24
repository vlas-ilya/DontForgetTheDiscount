package su.tease.project.feature.preset.api.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset

interface PresetInterceptor {

    suspend fun bankPresets(): PersistentList<BankPreset>

    suspend fun bankIconPresets(): PersistentList<BankIconPreset>

    suspend fun cashBackPresets(bankPresetId: String): PersistentList<CashBackPreset>

    suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset>

    suspend fun mccCodePresets(): PersistentList<MccCodePreset>

    suspend fun bankPreset(bankPresetId: String): BankPreset

    suspend fun cashBackPreset(cashBackPresetId: String): CashBackPreset

    suspend fun save(bankPreset: BankPreset)

    suspend fun save(cashBackPreset: CashBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
