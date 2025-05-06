package su.tease.project.feature.cacheback.data.repository

import android.database.sqlite.SQLiteException
import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.entity.EntityId
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.BankDao
import su.tease.project.feature.cacheback.data.dao.CacheBackCodeDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toEntity
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.CacheBackDate
import su.tease.project.feature.cacheback.domain.repository.BankRepository
import timber.log.Timber

class BankRepositoryImpl(
    private val bankDao: BankDao,
    private val cacheBackDao: CacheBackDao,
    private val cacheBackCodeDao: CacheBackCodeDao,
) : BankRepository {

    override suspend fun save(bank: Bank) = withDefault {
        try {
            bankDao.save(bank.toEntity())
            cacheBackDao.updateBy(bank.id.value, bank.cacheBacks.map { it.toEntity(bank.id) })
            bank.cacheBacks.forEach { cacheBack ->
                cacheBackCodeDao.updateBy(
                    cacheBack.id.value,
                    cacheBack.codes.map { it.toEntity(cacheBack.id) }
                )
            }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun find(id: EntityId<Bank>): Bank? = withDefault {
        try {
            bankDao.get(id.value)?.toDomain(cacheBackEntityList(id.value))
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun list(): PersistentList<Bank> = withDefault {
        try {
            bankDao.list().mapPersistent { it.toDomain(cacheBackEntityList(it.id)) }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun filterBy(date: CacheBackDate): PersistentList<Bank> =
        try {
            bankDao
                .filterBy(date.month, date.year)
                .mapPersistent { it.toDomain(cacheBackEntityList(it.id, date)) }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun listDates(): PersistentList<CacheBackDate> =
        try {
            cacheBackDao.listDates().mapPersistent { it.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun delete(id: EntityId<Bank>): Boolean = withDefault {
        try {
            val bank = find(id) ?: return@withDefault false
            bank.cacheBacks.forEach { cacheBackCodeDao.deleteBy(it.id.value) }
            cacheBackDao.deleteBy(id.value)
            bankDao.delete(bank.toEntity())
            true
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    private suspend fun cacheBackEntityList(bankId: String, cacheBackDate: CacheBackDate? = null) =
        try {
            val list = cacheBackDate
                ?.let { cacheBackDao.filterBy(bankId, it.month, it.year) }
                ?: cacheBackDao.list(bankId)
            list.mapPersistent { it.toDomain(cacheBackCodeEntityList(it.id)) }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    private suspend fun cacheBackCodeEntityList(cacheBackId: String) =
        try {
            cacheBackCodeDao.list(cacheBackId).mapPersistent { it.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
}
