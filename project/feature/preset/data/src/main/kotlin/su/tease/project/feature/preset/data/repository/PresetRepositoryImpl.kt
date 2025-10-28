package su.tease.project.feature.preset.data.repository

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.tryOrDefault
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.data.dao.PresetDao
import su.tease.project.feature.preset.data.dao.mapper.toDomain
import su.tease.project.feature.preset.data.dao.mapper.toEntity
import su.tease.project.feature.preset.data.dao.mapper.toEntityWithRelations
import su.tease.project.feature.preset.data.dataSource.mapper.MapperHelper
import su.tease.project.feature.preset.data.repository.exception.RepositoryException
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.repository.PresetRepository

class PresetRepositoryImpl(
    private val presetDao: PresetDao,
    private val cache: SimpleCache,
) : PresetRepository {

    private val mapperHelper = MapperHelper(presetDao)

    override suspend fun bankPresets(): PersistentList<BankPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANKS_CACHE_LOCAL) {
                presetDao.bankPresets()
                    .map { mapperHelper.run { it.toDomain() as BankPreset } }
                    .sortedBy { it.name }
                    .toPersistentList()
            }
        }
    }

    override suspend fun bankIcons(): PersistentList<BankIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(BANK_ICONS_CACHE_LOCAL) {
                presetDao.bankIconPresets().mapPersistent { it.toDomain() as BankIconPreset }
            }
        }
    }

    override suspend fun save(preset: BankIconPreset) = withDefault {
        presetDao.save(preset.toEntity())
        cache.clear(BANK_ICONS_CACHE_LOCAL)
    }

    override suspend fun shopPresets(): PersistentList<ShopPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(SHOPS_CACHE_LOCAL) {
                presetDao.shopPresets()
                    .map { mapperHelper.run { it.toDomain() as ShopPreset } }
                    .sortedBy { it.name }
                    .toPersistentList()
            }
        }
    }

    override suspend fun shopIconPresets(): PersistentList<ShopIconPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(SHOP_ICONS_CACHE_LOCAL) {
                presetDao.shopIconPresets().mapPersistent { it.toDomain() as ShopIconPreset }
            }
        }
    }

    override suspend fun save(preset: ShopIconPreset) = withDefault {
        presetDao.save(preset.toEntity())
        cache.clear(SHOP_ICONS_CACHE_LOCAL)
    }

    override suspend fun cashBackPresets(): PersistentList<CashBackPreset> = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache
                .getOrPut(CASH_BACKS_ALL_CACHE_LOCAL) {
                    presetDao.cashBackPresets()
                        .map { mapperHelper.run { it.toDomain() } }
                        .sortedBy { it.cashBackOwnerPreset.name + it.name }
                        .toPersistentList()
                }
        }
    }

    override suspend fun cashBackPresets(ownerPresetId: String) = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache
                .getOrPut(CASH_BACKS_CACHE_LOCAL) { SimpleCache() }
                .getOrPut(ownerPresetId) {
                    presetDao.cashBackPresets(ownerPresetId)
                        .map { mapperHelper.run { it.toDomain() } }
                        .sortedBy { it.name }
                        .toPersistentList()
                }
        }
    }

    override suspend fun cashBacksIconPresets() = withDefault {
        tryOrDefault(returnOnError = persistentListOf()) {
            cache.getOrPut(CASH_BACKS_ICONS_CACHE_LOCAL) {
                presetDao.cashBackIconPresets()
                    .sortedBy { it.iconUrl }
                    .mapPersistent { it.toDomain() }
            }
        }
    }

    override suspend fun save(preset: CashBackIconPreset) = withDefault {
        presetDao.save(preset.toEntity())
        cache.clear(CASH_BACKS_ICONS_CACHE_LOCAL)
    }

    override suspend fun mccCodePresets() = withDefault {
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
                    mapperHelper.run { presetDao.bankPreset(bankPresetId).toDomain() as BankPreset }
                }
        }
    }

    override suspend fun bankPresets(bankPresetIds: List<String>) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            val cache = cache.getOrPut(BANK_CACHE_LOCAL) { SimpleCache() }
            val cachedBankPresets = bankPresetIds.mapNotNull { cache.get<BankPreset>(it) }
            val cachedBankPresetIds = cachedBankPresets.map { it.id }
            val nonCachedBankPresetIds = bankPresetIds.subtract(cachedBankPresetIds).toList()
            val nonCachedBankPresets = presetDao.bankPresets(nonCachedBankPresetIds)
                .map { mapperHelper.run { it.toDomain() as BankPreset } }
                .onEach { cache.put(it.id, it) }
            (cachedBankPresets + nonCachedBankPresets).toPersistentList()
        }
    }

    override suspend fun shopPreset(shopPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache
                .getOrPut(SHOP_CACHE_LOCAL) { SimpleCache() }
                .getOrPut(shopPresetId) {
                    mapperHelper.run { presetDao.shopPreset(shopPresetId).toDomain() as ShopPreset }
                }
        }
    }

    override suspend fun shopPresets(shopPresetIds: List<String>) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            val cache = cache.getOrPut(SHOP_CACHE_LOCAL) { SimpleCache() }
            val cachedShopPresets = shopPresetIds.mapNotNull { cache.get<ShopPreset>(it) }
            val cachedShopPresetIds = cachedShopPresets.map { it.id }
            val nonCachedShopPresetIds = shopPresetIds.subtract(cachedShopPresetIds).toList()
            val nonCachedShopPresets = presetDao.shopPresets(nonCachedShopPresetIds)
                .map { mapperHelper.run { it.toDomain() as ShopPreset } }
                .onEach { cache.put(it.id, it) }
            (cachedShopPresets + nonCachedShopPresets).toPersistentList()
        }
    }

    override suspend fun cashBackPreset(cashBackPresetId: String) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            cache
                .getOrPut(CASH_BACK_CACHE_LOCAL) { SimpleCache() }
                .getOrPut(cashBackPresetId) {
                    mapperHelper.run { presetDao.cashBackPreset(cashBackPresetId).toDomain() }
                }
        }
    }

    override suspend fun cashBackPresets(cashBackPresetIds: List<String>) = withDefault {
        tryOrDefault(returnOnError = { throw RepositoryException() }) {
            val cache = cache.getOrPut(CASH_BACK_CACHE_LOCAL) { SimpleCache() }
            val cachedCashBackPresets =
                cashBackPresetIds.mapNotNull { cache.get<CashBackPreset>(it) }
            val cachedCashBackPresetIds = cachedCashBackPresets.map { it.id }
            val nonCachedCashBackPresetIds =
                cashBackPresetIds.subtract(cachedCashBackPresetIds).toList()
            val nonCachedCashBackPresets = presetDao.cashBackPresets(nonCachedCashBackPresetIds)
                .map { mapperHelper.run { it.toDomain() } }
                .onEach { cache.put(it.id, it) }
            (cachedCashBackPresets + nonCachedCashBackPresets).toPersistentList()
        }
    }

    override suspend fun save(cashBackOwnerPreset: BankPreset) = withDefault {
        cashBackOwnerPreset.iconPreset.toEntity().let { presetDao.save(it) }
        cashBackOwnerPreset.toEntity().let { presetDao.save(it) }

        cache.clear(BANK_ICONS_CACHE_LOCAL)
        cache.clear(BANKS_CACHE_LOCAL)
        cache.getOrPut(BANK_CACHE_LOCAL) { SimpleCache() }.clear(cashBackOwnerPreset.id)
    }

    override suspend fun save(shopPreset: ShopPreset) = withDefault {
        shopPreset.iconPreset.toEntity().let { presetDao.save(it) }
        shopPreset.toEntity().let { presetDao.save(it) }

        cache.clear(SHOP_ICONS_CACHE_LOCAL)
        cache.clear(SHOPS_CACHE_LOCAL)
        cache.getOrPut(SHOP_CACHE_LOCAL) { SimpleCache() }.clear(shopPreset.id)
    }

    override suspend fun save(cashBackPreset: CashBackPreset) = withDefault {
        save(cashBackPreset.cashBackOwnerPreset)

        cashBackPreset.iconPreset.toEntity().let { presetDao.save(it) }
        cashBackPreset.mccCodes.forEach { save(it) }

        val (cashBackEntity, relationEntities) = cashBackPreset.toEntityWithRelations()
        presetDao.save(cashBackEntity)

        presetDao.removeMccCodeRelations(cashBackPreset.id)
        relationEntities.forEach { presetDao.save(it) }

        cache.clear(CASH_BACKS_ICONS_CACHE_LOCAL)
        cache.clear(MCC_CODES_CACHE_LOCAL)
        cache.clear(CASH_BACKS_CACHE_LOCAL)
        cache.clear(CASH_BACKS_ALL_CACHE_LOCAL)
        cache.getOrPut(CASH_BACK_CACHE_LOCAL) { SimpleCache() }.clear(cashBackPreset.id)
    }

    private suspend fun save(preset: CashBackOwnerPreset) = when (preset) {
        is BankPreset -> save(preset)
        is ShopPreset -> save(preset)
    }

    override suspend fun save(mccCodePreset: MccCodePreset) = withDefault {
        mccCodePreset.toEntity().let { presetDao.save(it) }
    }

    companion object {
        private const val BANKS_CACHE_LOCAL = "BANKS_CACHE_LOCAL"
        private const val BANK_CACHE_LOCAL = "BANK_CACHE_LOCAL"
        private const val BANK_ICONS_CACHE_LOCAL = "BANK_ICONS_CACHE_LOCAL"
        private const val SHOPS_CACHE_LOCAL = "SHOPS_CACHE_LOCAL"
        private const val SHOP_CACHE_LOCAL = "SHOP_CACHE_LOCAL"
        private const val SHOP_ICONS_CACHE_LOCAL = "SHOP_ICONS_CACHE_LOCAL"
        private const val CASH_BACKS_CACHE_LOCAL = "CASH_BACKS_CACHE_LOCAL"
        private const val CASH_BACKS_ALL_CACHE_LOCAL = "CASH_BACKS_ALL_CACHE_LOCAL"
        private const val CASH_BACK_CACHE_LOCAL = "CASH_BACK_CACHE_LOCAL"
        private const val CASH_BACKS_ICONS_CACHE_LOCAL = "CASH_BACKS_ICONS_CACHE_LOCAL"
        private const val MCC_CODES_CACHE_LOCAL = "MCC_CODES_CACHE_LOCAL"
    }
}
