package su.tease.project.feature.preset.impl.data.repository

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.tryOrDefault
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CacheBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.impl.data.dao.PresetDao
import su.tease.project.feature.preset.impl.data.dao.mapper.toDomain
import su.tease.project.feature.preset.impl.data.dao.mapper.toEntity
import su.tease.project.feature.preset.impl.data.dao.mapper.toEntityWithRelations
import su.tease.project.feature.preset.impl.data.dataSource.mapper.MapperHelper
import su.tease.project.feature.preset.impl.domain.repository.PresetRepository

class PresetRepositoryImpl(
    private val presetDao: PresetDao,
    private val cache: SimpleCache,
) : PresetRepository {

    private val mapperHelper = MapperHelper(presetDao)

    override suspend fun banks(): PersistentList<BankPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANKS_CACHE_LOCAL) {
                presetDao.bankPresets()
                    .map { mapperHelper.run { it.toDomain() } }
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
                cache
                    .getOrPut(CACHE_BACK_CACHE_LOCAL) { SimpleCache() }
                    .getOrPut(bankPresetId) {
                        presetDao.cacheBackPresets(bankPresetId)
                            .map { mapperHelper.run { it.toDomain() } }
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

    override suspend fun mccCodes() = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(MCC_CODES_CACHE_LOCAL) {
                presetDao.mccCodePresets()
                    .sortedBy { it.code }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun bankPreset(bankPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache
                .getOrPut(BANK_CACHE_LOCAL) { SimpleCache() }
                .getOrPut(bankPresetId) {
                    mapperHelper.run { presetDao.bankPreset(bankPresetId).toDomain() }
                }
        }
    }

    override suspend fun cacheBackPreset(cacheBackPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache.getOrPut("$CACHE_BACKS_CACHE_LOCAL cacheBack $cacheBackPresetId") {
                mapperHelper.run { presetDao.cacheBackPreset(cacheBackPresetId).toDomain() }
            }
        }
    }

    override suspend fun save(bankPreset: BankPreset) = withDefault {
        bankPreset.iconPreset.toEntity().let { presetDao.save(it) }
        bankPreset.toEntity().let { presetDao.save(it) }

        cache.clear(BANK_ICONS_CACHE_LOCAL)
        cache.clear(BANKS_CACHE_LOCAL)
        cache.getOrPut(BANK_CACHE_LOCAL) { SimpleCache() }.clear(bankPreset.id)
    }

    override suspend fun save(cacheBackPreset: CacheBackPreset) = withDefault {
        save(cacheBackPreset.bankPreset)

        cacheBackPreset.iconPreset.toEntity().let { presetDao.save(it) }
        cacheBackPreset.mccCodes.forEach { save(it) }

        val (cacheBackEntity, relationEntities) = cacheBackPreset.toEntityWithRelations()
        presetDao.save(cacheBackEntity)

        presetDao.removeMccCodeRelations(cacheBackPreset.id)
        relationEntities.forEach { presetDao.save(it) }

        cache.clear(CACHE_BACKS_ICONS_CACHE_LOCAL)
        cache.clear(MCC_CODES_CACHE_LOCAL)
        cache.clear(CACHE_BACKS_CACHE_LOCAL)
        cache.getOrPut(CACHE_BACK_CACHE_LOCAL) { SimpleCache() }.clear(cacheBackPreset.id)
    }

    override suspend fun save(mccCodePreset: MccCodePreset) = withDefault {
        mccCodePreset.toEntity().let { presetDao.save(it) }
    }

    companion object {
        private const val BANKS_CACHE_LOCAL = "BANKS_CACHE_LOCAL"
        private const val BANK_CACHE_LOCAL = "BANK_CACHE_LOCAL"
        private const val BANK_ICONS_CACHE_LOCAL = "BANK_ICONS_CACHE_LOCAL"
        private const val CACHE_BACKS_CACHE_LOCAL = "CACHE_BACKS_CACHE_LOCAL"
        private const val CACHE_BACK_CACHE_LOCAL = "CACHE_BACK_CACHE_LOCAL"
        private const val CACHE_BACKS_ICONS_CACHE_LOCAL = "CACHE_BACKS_ICONS_CACHE_LOCAL"
        private const val MCC_CODES_CACHE_LOCAL = "CACHE_BACK_CODES_CACHE_LOCAL"
    }
}
