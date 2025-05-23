package su.tease.project.feature.preset.api.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset

interface PresetInterceptor {

    suspend fun bankPresets(): PersistentList<BankPreset>

    suspend fun bankIconPresets(): PersistentList<BankIconPreset>

    suspend fun cacheBackPresets(bankPresetId: String): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIconPresets(): PersistentList<CacheBackIconPreset>

    suspend fun mccCodePresets(): PersistentList<MccCodePreset>

    suspend fun bankPreset(bankPresetId: String): BankPreset

    suspend fun cacheBackPreset(cacheBackPresetId: String): CacheBackPreset

    suspend fun save(bankPreset: BankPreset)

    suspend fun save(cacheBackPreset: CacheBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
