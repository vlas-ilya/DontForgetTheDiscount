package su.tease.project.feature.cacheback.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

interface PresetRepository {

    suspend fun banks(): PersistentList<BankPreset>

    suspend fun bankIcons(): PersistentList<BankIconPreset>

    suspend fun cacheBacks(bankPresetId: String): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIcons(): PersistentList<CacheBackIconPreset>

    suspend fun mccCodes(): PersistentList<MccCodePreset>

    suspend fun cacheBackPreset(cacheBackPresetId: String): CacheBackPreset

    suspend fun save(bank: BankPreset)

    suspend fun save(cacheBack: CacheBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
