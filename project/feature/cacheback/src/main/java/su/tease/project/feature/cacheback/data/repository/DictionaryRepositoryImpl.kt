package su.tease.project.feature.cacheback.data.repository

import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dataSource.DictionaryDataSource
import su.tease.project.feature.cacheback.data.dataSource.mapper.toDomain
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(
    private val dataSource: DictionaryDataSource,
    private val cache: SimpleCache,
) : DictionaryRepository {

    override suspend fun banks(): List<BankPreset> = withDefault {
        cache.getOrPut(BANKS_CACHE) {
            dataSource.banks().map { it.toDomain() }
        }
    }

    override suspend fun cacheBacks(): List<CacheBackPreset> = withDefault {
        cache.getOrPut(CACHE_BACKS_CACHE) {
            dataSource.cacheBacks().map { it.toDomain() }
        }
    }

    override suspend fun cacheBacksIcons(): List<CacheBackIcon> = withDefault {
        cache.getOrPut(CACHE_BACKS_ICONS_CACHE) {
            dataSource.cacheBacksIcons().map { it.toDomain() }
        }
    }

    companion object {
        private const val BANKS_CACHE = "BANKS_CACHE"
        private const val CACHE_BACKS_CACHE = "CACHE_BACKS_CACHE"
        private const val CACHE_BACKS_ICONS_CACHE = "CACHE_BACKS_ICONS_CACHE"
    }
}
