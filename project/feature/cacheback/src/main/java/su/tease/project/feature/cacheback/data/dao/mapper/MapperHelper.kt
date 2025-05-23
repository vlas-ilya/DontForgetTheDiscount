package su.tease.project.feature.cacheback.data.dao.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor

internal class MapperHelper(
    private val cacheBackDao: CacheBackDao,
    private val presetInterceptor: PresetInterceptor,
) {
    suspend fun BankAccountEntity.toDomain() =
        toDomain(
            getBankPreset = { getBankPreset(it) },
            getCacheBacks = { getCacheBacks(it) }
        )

    suspend fun BankAccountEntity.toDomain(cacheBackMonth: Int, cacheBackYear: Int) =
        toDomain(
            getBankPreset = { getBankPreset(it) },
            getCacheBacks = { getCacheBacks(it, cacheBackMonth, cacheBackYear) }
        )

    private suspend fun getBankPreset(bankPresetId: String) = presetInterceptor
        .bankPreset(bankPresetId)

    private suspend fun getCacheBacks(bankAccountId: String) = cacheBackDao
        .filterBy(bankAccountId)
        .mapPersistent { it.toDomain() }

    private suspend fun getCacheBacks(
        bankAccountId: String,
        cacheBackMonth: Int,
        cacheBackYear: Int
    ) = cacheBackDao
        .filterBy(bankAccountId, cacheBackMonth, cacheBackYear)
        .mapPersistent { it.toDomain() }

    private suspend fun CacheBackEntity.toDomain() =
        toDomain(getCacheBackPreset = { getCacheBackPreset(it) })

    private suspend fun getCacheBackPreset(cacheBackPresetId: String) = presetInterceptor
        .cacheBackPreset(cacheBackPresetId)
}
