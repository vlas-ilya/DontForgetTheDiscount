package su.tease.project.feature.preset.impl.domain.interseptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.impl.domain.repository.PresetRepository

class PresetInterceptorImpl(
    private val repository: PresetRepository
) : PresetInterceptor {

    override suspend fun bankPresets(): PersistentList<BankPreset> =
        withDefault { repository.banks() }

    override suspend fun bankIconPresets(): PersistentList<BankIconPreset> =
        withDefault { repository.bankIcons() }

    override suspend fun cacheBackPresets(bankPresetId: String): PersistentList<CacheBackPreset> =
        withDefault { repository.cacheBacks(bankPresetId) }

    override suspend fun cacheBacksIconPresets(): PersistentList<CacheBackIconPreset> =
        withDefault { repository.cacheBacksIcons() }

    override suspend fun mccCodePresets(): PersistentList<MccCodePreset> =
        withDefault { repository.mccCodes() }

    override suspend fun bankPreset(bankPresetId: String) =
        withDefault { repository.bankPreset(bankPresetId) }

    override suspend fun cacheBackPreset(cacheBackPresetId: String) =
        withDefault { repository.cacheBackPreset(cacheBackPresetId) }

    override suspend fun save(bankPreset: BankPreset) =
        withDefault { repository.save(bankPreset) }

    override suspend fun save(cacheBackPreset: CacheBackPreset) =
        withDefault { repository.save(cacheBackPreset) }

    override suspend fun save(mccCodePreset: MccCodePreset) =
        withDefault { repository.save(mccCodePreset) }
}
