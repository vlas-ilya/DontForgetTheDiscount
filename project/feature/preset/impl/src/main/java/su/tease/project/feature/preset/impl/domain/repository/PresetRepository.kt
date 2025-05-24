package su.tease.project.feature.preset.impl.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset

interface PresetRepository {

    suspend fun banks(): PersistentList<BankPreset>

    suspend fun bankIcons(): PersistentList<BankIconPreset>

    suspend fun cashBacks(bankPresetId: String): PersistentList<CashBackPreset>

    suspend fun cashBacksIcons(): PersistentList<CashBackIconPreset>

    suspend fun mccCodes(): PersistentList<MccCodePreset>

    suspend fun bankPreset(bankPresetId: String): BankPreset

    suspend fun cashBackPreset(cashBackPresetId: String): CashBackPreset

    suspend fun save(bankPreset: BankPreset)

    suspend fun save(cashBackPreset: CashBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
