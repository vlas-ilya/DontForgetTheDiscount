package su.tease.project.feature.cacheback.data.repository

import android.database.sqlite.SQLiteException
import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.BankAccountDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.PresetDao
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toEntity
import su.tease.project.feature.cacheback.data.utils.BankMapperHelper
import su.tease.project.feature.cacheback.domain.entity.BankAccount
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.repository.BankAccountRepository
import timber.log.Timber

class BankAccountRepositoryImpl(
    private val bankAccountDao: BankAccountDao,
    private val cacheBackDao: CacheBackDao,
    presetDao: PresetDao,
) : BankAccountRepository {

    private val bankMapperHelper = BankMapperHelper(cacheBackDao, presetDao)

    override suspend fun save(value: BankAccount) = withDefault {
        try {
            bankAccountDao.findById(value.id)?.run { bankMapperHelper.removeCacheBacks(id) }
            bankAccountDao.save(value.toEntity())
            value.cacheBacks.forEach { bankMapperHelper.saveCacheBack(it, value.id) }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun find(id: String): BankAccount? = withDefault {
        try {
            val bankAccountDto = bankAccountDao.findById(id) ?: return@withDefault null
            bankMapperHelper.run { bankAccountDto.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun list(): PersistentList<BankAccount> = withDefault {
        try {
            bankAccountDao.list()
                .mapPersistent { bankMapperHelper.run { it.toDomain() } }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun filterBy(date: CacheBackDate): PersistentList<BankAccount> =
        try {
            bankAccountDao
                .filterBy(date.month, date.year)
                .mapPersistent { bankMapperHelper.run { it.toDomain(date.month, date.year) } }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun listDates(): PersistentList<CacheBackDate> =
        try {
            cacheBackDao.dates().mapPersistent { it.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun delete(id: String): Boolean = withDefault {
        try {
            bankAccountDao.findById(id)?.run {
                bankMapperHelper.removeCacheBacks(id)
                bankAccountDao.delete(id)
                true
            } ?: false
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }
}
