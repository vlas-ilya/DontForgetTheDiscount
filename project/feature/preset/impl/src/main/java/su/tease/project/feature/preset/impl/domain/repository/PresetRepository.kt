package su.tease.project.feature.preset.impl.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset

interface PresetRepository {

    suspend fun banks(): PersistentList<BankPreset>

    suspend fun bankIcons(): PersistentList<BankIconPreset>

    suspend fun cacheBacks(bankPresetId: String): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIcons(): PersistentList<CacheBackIconPreset>

    suspend fun mccCodes(): PersistentList<MccCodePreset>

    suspend fun bankPreset(bankPresetId: String): BankPreset

    suspend fun cacheBackPreset(cacheBackPresetId: String): CacheBackPreset

    suspend fun save(bankPreset: BankPreset)

    suspend fun save(cacheBackPreset: CacheBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
