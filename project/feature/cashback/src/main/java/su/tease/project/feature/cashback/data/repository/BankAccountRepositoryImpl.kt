package su.tease.project.feature.cashback.data.repository

import android.database.sqlite.SQLiteException
import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.repository.RepositoryException
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cashback.data.dao.BankAccountDao
import su.tease.project.feature.cashback.data.dao.CashBackDao
import su.tease.project.feature.cashback.data.dao.mapper.MapperHelper
import su.tease.project.feature.cashback.data.dao.mapper.toDomain
import su.tease.project.feature.cashback.data.dao.mapper.toEntity
import su.tease.project.feature.cashback.domain.entity.BankAccount
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.repository.BankAccountRepository
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import timber.log.Timber

class BankAccountRepositoryImpl(
    private val bankAccountDao: BankAccountDao,
    private val cashBackDao: CashBackDao,
    presetInterceptor: PresetInterceptor,
) : BankAccountRepository {

    private val mapperHelper = MapperHelper(cashBackDao, presetInterceptor)

    override suspend fun save(value: BankAccount) = withDefault {
        try {
            bankAccountDao.findById(value.id)?.run {
                cashBackDao.filterBy(id).forEach { cashBackDao.remove(it.id) }
            }
            bankAccountDao.save(value.toEntity())
            value.cashBacks.forEach { cashBackDao.save(it.toEntity(value.id)) }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun find(id: String): BankAccount? = withDefault {
        try {
            val bankAccountDto = bankAccountDao.findById(id) ?: return@withDefault null
            mapperHelper.run { bankAccountDto.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun list(): PersistentList<BankAccount> = withDefault {
        try {
            bankAccountDao.list()
                .mapPersistent { mapperHelper.run { it.toDomain() } }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun filterBy(date: CashBackDate): PersistentList<BankAccount> =
        try {
            bankAccountDao
                .filterBy(date.month, date.year)
                .mapPersistent { mapperHelper.run { it.toDomain(date.month, date.year) } }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun listDates(): PersistentList<CashBackDate> =
        try {
            cashBackDao.dates().mapPersistent { it.toDomain() }
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }

    override suspend fun delete(id: String): Boolean = withDefault {
        try {
            bankAccountDao.findById(id)?.run {
                cashBackDao.filterBy(id).forEach { cashBackDao.remove(it.id) }
                bankAccountDao.delete(id)
                true
            } ?: false
        } catch (e: SQLiteException) {
            Timber.e(e)
            throw RepositoryException()
        }
    }
}
