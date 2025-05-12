package su.tease.project.feature.cacheback.data.utils

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.PresetDao
import su.tease.project.feature.cacheback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.cacheback.data.dao.entity.preset.BankPresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.preset.CacheBackPresetEntity
import su.tease.project.feature.cacheback.data.dao.mapper.preset.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toEntity
import su.tease.project.feature.cacheback.domain.entity.CacheBack

@Suppress("TooManyFunctions")
internal class BankMapperHelper(
    private val cacheBackDao: CacheBackDao,
    private val presetDao: PresetDao,
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

    private suspend fun getBankPreset(bankPresetId: String) = presetDao
        .bankPreset(bankPresetId)
        .toDomain()

    suspend fun BankPresetEntity.toDomain() =
        toDomain(getIcon = { getBankIconPreset(it) })

    private suspend fun getBankIconPreset(iconPresetId: String) =
        presetDao.bankIconPreset(iconPresetId).toDomain()

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

    private suspend fun getCacheBackPreset(cacheBackPresetId: String) = presetDao
        .cacheBackPreset(cacheBackPresetId)
        .toDomain()

    suspend fun CacheBackPresetEntity.toDomain() =
        toDomain(
            getIconPreset = { getCacheBackIconPreset(it) },
            getBankPreset = { getBankPreset(it) },
            getCacheBackCodePresets = { getCacheBackCodePresets(it) }
        )

    private suspend fun getCacheBackIconPreset(iconPresetId: String) = presetDao
        .cacheBackIconPreset(iconPresetId)
        .toDomain()

    private suspend fun getCacheBackCodePresets(cacheBackPresetId: String) = presetDao
        .mccCodeRelations(cacheBackPresetId)
        .mapPersistent { getMccCode(it.mccCodePresetId) }

    private suspend fun getMccCode(mccCodePresetId: String) = presetDao
        .mccCodePreset(mccCodePresetId)
        .toDomain()

    suspend fun removeCacheBacks(bankEntityId: String) {
        cacheBackDao.filterBy(bankEntityId).forEach { cacheBackDao.remove(it.id) }
    }

    suspend fun saveCacheBack(
        cacheBack: CacheBack,
        bankAccountId: String
    ) {
        cacheBackDao.save(cacheBack.toEntity(bankAccountId))
    }
}
