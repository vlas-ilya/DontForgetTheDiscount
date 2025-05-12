package su.tease.project.feature.cacheback.data.repository

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.tryOrDefault
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.PresetDao
import su.tease.project.feature.cacheback.data.dao.mapper.preset.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.preset.toEntity
import su.tease.project.feature.cacheback.data.dao.mapper.preset.toEntityWithRelations
import su.tease.project.feature.cacheback.data.utils.BankMapperHelper
import su.tease.project.feature.cacheback.domain.entity.preset.BankIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIconPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.cacheback.domain.repository.PresetRepository

class PresetRepositoryImpl(
    cacheBackDao: CacheBackDao,
    private val presetDao: PresetDao,
    private val cache: SimpleCache,
) : PresetRepository {

    private val bankMapperHelper = BankMapperHelper(cacheBackDao, presetDao)

    override suspend fun banks(): PersistentList<BankPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANKS_CACHE_LOCAL) {
                presetDao.bankPresets()
                    .map { bankMapperHelper.run { it.toDomain() } }
                    .sortedBy { it.name }
                    .toPersistentList()
            }
        }
    }

    override suspend fun bankIcons(): PersistentList<BankIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANK_ICONS_CACHE_LOCAL) {
                presetDao.bankIconPresets().mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun cacheBacks(bankPresetId: String): PersistentList<CacheBackPreset> =
        withDefault {
            tryOrDefault(returnOnError = persistentListOf()) {
                cache.getOrPut("$CACHE_BACKS_CACHE_LOCAL bank $bankPresetId") {
                    presetDao.cacheBackPresets(bankPresetId)
                        .map { bankMapperHelper.run { it.toDomain() } }
                        .sortedBy { it.name }
                        .toPersistentList()
                }
            }
        }

    override suspend fun cacheBacksIcons(): PersistentList<CacheBackIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(CACHE_BACKS_ICONS_CACHE_LOCAL) {
                presetDao.cacheBackIconPresets()
                    .sortedBy { it.iconUrl }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun mccCodes(): PersistentList<MccCodePreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(MCC_CODES_CACHE_LOCAL) {
                presetDao.mccCodePresets()
                    .sortedBy { it.code }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun cacheBackPreset(cacheBackPresetId: String): CacheBackPreset = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache.getOrPut("$CACHE_BACKS_CACHE_LOCAL cacheBack $cacheBackPresetId") {
                bankMapperHelper.run { presetDao.cacheBackPreset(cacheBackPresetId).toDomain() }
            }
        }
    }

    override suspend fun save(bank: BankPreset) = withDefault {
        bank.iconPreset.toEntity().let { presetDao.save(it) }
        bank.toEntity().let { presetDao.save(it) }
        cache.clear()
    }

    override suspend fun save(cacheBack: CacheBackPreset) = withDefault {
        save(cacheBack.bankPreset)

        cacheBack.iconPreset.toEntity().let { presetDao.save(it) }
        cacheBack.mccCodes.forEach { save(it) }

        val (cacheBackEntity, relationEntities) = cacheBack.toEntityWithRelations()
        presetDao.save(cacheBackEntity)

        presetDao.removeMccCodeRelations(cacheBack.id)
        relationEntities.forEach { presetDao.save(it) }

        cache.clear()
    }

    override suspend fun save(mccCodePreset: MccCodePreset) = withDefault {
        mccCodePreset.toEntity().let { presetDao.save(it) }
    }

    companion object {
        private const val BANKS_CACHE_LOCAL = "BANKS_CACHE_LOCAL"
        private const val BANK_ICONS_CACHE_LOCAL = "BANK_ICONS_CACHE_LOCAL"
        private const val CACHE_BACKS_CACHE_LOCAL = "CACHE_BACKS_CACHE_LOCAL"
        private const val CACHE_BACKS_ICONS_CACHE_LOCAL = "CACHE_BACKS_ICONS_CACHE_LOCAL"
        private const val MCC_CODES_CACHE_LOCAL = "CACHE_BACK_CODES_CACHE_LOCAL"
    }
}
