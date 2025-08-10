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
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
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
            cache.getOrPut(BANKS_CASH_LOCAL) {
                presetDao.bankPresets()
                    .map { mapperHelper.run { it.toDomain() } }
                    .sortedBy { it.name }
                    .toPersistentList()
            }
        }
    }

    override suspend fun bankIcons(): PersistentList<BankIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANK_ICONS_CASH_LOCAL) {
                presetDao.bankIconPresets().mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun cashBacks(bankPresetId: String): PersistentList<CashBackPreset> =
        withDefault {
            tryOrDefault(returnOnError = persistentListOf()) {
                cache
                    .getOrPut(CASH_BACKS_CASH_LOCAL) { SimpleCache() }
                    .getOrPut(bankPresetId) {
                        presetDao.cashBackPresets(bankPresetId)
                            .map { mapperHelper.run { it.toDomain() } }
                            .sortedBy { it.name }
                            .toPersistentList()
                    }
            }
        }

    override suspend fun cashBacksIcons(): PersistentList<CashBackIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(CASH_BACKS_ICONS_CASH_LOCAL) {
                presetDao.cashBackIconPresets()
                    .sortedBy { it.iconUrl }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun mccCodes() = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(MCC_CODES_CASH_LOCAL) {
                presetDao.mccCodePresets()
                    .sortedBy { it.code }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun bankPreset(bankPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache
                .getOrPut(BANK_CASH_LOCAL) { SimpleCache() }
                .getOrPut(bankPresetId) {
                    mapperHelper.run { presetDao.bankPreset(bankPresetId).toDomain() }
                }
        }
    }

    override suspend fun cashBackPreset(cashBackPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache
                .getOrPut(CASH_BACK_CASH_LOCAL) { SimpleCache() }
                .getOrPut(cashBackPresetId) {
                    mapperHelper.run { presetDao.cashBackPreset(cashBackPresetId).toDomain() }
                }
        }
    }

    override suspend fun save(bankPreset: BankPreset) = withDefault {
        bankPreset.iconPreset.toEntity().let { presetDao.save(it) }
        bankPreset.toEntity().let { presetDao.save(it) }

        cache.clear(BANK_ICONS_CASH_LOCAL)
        cache.clear(BANKS_CASH_LOCAL)
        cache.getOrPut(BANK_CASH_LOCAL) { SimpleCache() }.clear(bankPreset.id)
    }

    override suspend fun save(cashBackPreset: CashBackPreset) = withDefault {
        save(cashBackPreset.bankPreset)

        cashBackPreset.iconPreset.toEntity().let { presetDao.save(it) }
        cashBackPreset.mccCodes.forEach { save(it) }

        val (cashBackEntity, relationEntities) = cashBackPreset.toEntityWithRelations()
        presetDao.save(cashBackEntity)

        presetDao.removeMccCodeRelations(cashBackPreset.id)
        relationEntities.forEach { presetDao.save(it) }

        cache.clear(CASH_BACKS_ICONS_CASH_LOCAL)
        cache.clear(MCC_CODES_CASH_LOCAL)
        cache.clear(CASH_BACKS_CASH_LOCAL)
        cache.getOrPut(CASH_BACK_CASH_LOCAL) { SimpleCache() }.clear(cashBackPreset.id)
    }

    override suspend fun save(mccCodePreset: MccCodePreset) = withDefault {
        mccCodePreset.toEntity().let { presetDao.save(it) }
    }

    companion object {
        private const val BANKS_CASH_LOCAL = "BANKS_CASH_LOCAL"
        private const val BANK_CASH_LOCAL = "BANK_CASH_LOCAL"
        private const val BANK_ICONS_CASH_LOCAL = "BANK_ICONS_CASH_LOCAL"
        private const val CASH_BACKS_CASH_LOCAL = "CASH_BACKS_CASH_LOCAL"
        private const val CASH_BACK_CASH_LOCAL = "CASH_BACK_CASH_LOCAL"
        private const val CASH_BACKS_ICONS_CASH_LOCAL = "CASH_BACKS_ICONS_CASH_LOCAL"
        private const val MCC_CODES_CASH_LOCAL = "CASH_BACK_CODES_CASH_LOCAL"
    }
}
