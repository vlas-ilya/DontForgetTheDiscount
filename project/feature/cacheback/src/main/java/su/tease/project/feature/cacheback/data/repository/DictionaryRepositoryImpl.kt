package su.tease.project.feature.cacheback.data.repository

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import su.tease.project.core.utils.cache.SimpleCache
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.DictionaryDao
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toEntity
import su.tease.project.feature.cacheback.data.dataSource.DictionaryDataSource
import su.tease.project.feature.cacheback.data.dataSource.mapper.toDomain
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(
    private val dataSource: DictionaryDataSource,
    private val dao: DictionaryDao,
    private val cache: SimpleCache,
) : DictionaryRepository {

    override suspend fun banks(): PersistentList<BankPreset> = withDefault {
        val remote = asyncAndCache(BANKS_CACHE_REMOTE) {
            dataSource.banks().map { it.toDomain() }
        }
        val local = asyncAndCache(BANKS_CACHE_LOCAL) {
            dao.banks().map { it.toDomain() }
        }
        (remote.await() + local.await()).sortedBy { it.name }.toPersistentList()
    }

    override suspend fun cacheBacks(): PersistentList<CacheBackPreset> = withDefault {
        val remote = asyncAndCache(CACHE_BACKS_CACHE_REMOTE) {
            dataSource.cacheBacks().map { it.toDomain() }
        }
        val local = asyncAndCache(CACHE_BACKS_CACHE_LOCAL) {
            dao.cacheBacks().map { it.toDomain() }
        }
        (remote.await() + local.await()).sortedBy { it.name }.toPersistentList()
    }

    override suspend fun cacheBacksIcons(): PersistentList<IconPreset> = withDefault {
        cache.getOrPut(CACHE_BACKS_ICONS_CACHE_REMOTE) {
            dataSource.cacheBacksIcons().map { it.toDomain() }
        }.sortedBy { it.url }.toPersistentList()
    }

    override suspend fun add(bank: BankPreset) = withDefault {
        dao.add(bank.toEntity())
        cache.clear(BANKS_CACHE_LOCAL)
    }

    override suspend fun add(cacheBank: CacheBackPreset) = withDefault {
        dao.add(cacheBank.toEntity())
        cache.clear(CACHE_BACKS_CACHE_LOCAL)
    }

    private inline fun <T> CoroutineScope.asyncAndCache(
        key: Any,
        crossinline defaultValue: suspend () -> T
    ) = async { cache.getOrPut(key) { defaultValue() } }

    companion object {
        private const val BANKS_CACHE_REMOTE = "BANKS_CACHE_REMOTE"
        private const val CACHE_BACKS_CACHE_REMOTE = "CACHE_BACKS_CACHE_REMOTE"
        private const val BANKS_CACHE_LOCAL = "BANKS_CACHE_LOCAL"
        private const val CACHE_BACKS_CACHE_LOCAL = "CACHE_BACKS_CACHE_LOCAL"
        private const val CACHE_BACKS_ICONS_CACHE_REMOTE = "CACHE_BACKS_ICONS_CACHE_REMOTE"
    }
}
