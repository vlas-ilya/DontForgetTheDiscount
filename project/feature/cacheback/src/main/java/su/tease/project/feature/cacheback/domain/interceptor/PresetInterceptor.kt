package su.tease.project.feature.cacheback.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset

interface PresetInterceptor {

    suspend fun bankPresets(): PersistentList<BankPreset>

    suspend fun bankIconPresets(): PersistentList<BankIconPreset>

    suspend fun cacheBackPresets(bankPresetId: String): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIconPresets(): PersistentList<CacheBackIconPreset>

    suspend fun mccCodePresets(): PersistentList<MccCodePreset>

    suspend fun cacheBackPreset(cacheBackPresetId: String): CacheBackPreset

    suspend fun save(bankPreset: BankPreset)

    suspend fun save(cacheBackPreset: CacheBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
