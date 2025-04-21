package su.tease.project.feature.cacheback.data.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.entity.EntityId
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.BankDao
import su.tease.project.feature.cacheback.data.dao.CacheBackCodeDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toEntity
import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.repository.BankRepository

class BankRepositoryImpl(
    private val cacheBackBankDao: BankDao,
    private val cacheBackDao: CacheBackDao,
    private val cacheBackCodeDao: CacheBackCodeDao,
) : BankRepository {

    override suspend fun save(bank: Bank) = withDefault {
        cacheBackBankDao.save(bank.toEntity())
        cacheBackDao.updateBy(bank.id.value, bank.cacheBacks.map { it.toEntity(bank.id) })
        bank.cacheBacks.forEach { cacheBack ->
            cacheBackCodeDao.updateBy(
                cacheBack.id.value,
                cacheBack.codes.map { it.toEntity(cacheBack.id) }
            )
        }
    }

    override suspend fun find(id: EntityId<Bank>): Bank? = withDefault {
        cacheBackBankDao.get(id.value)?.toDomain(
            cacheBackEntityList(id.value)
        )
    }

    override suspend fun list(): PersistentList<Bank> = withDefault {
        cacheBackBankDao.list().mapPersistent {
            it.toDomain(cacheBackEntityList(it.id))
        }
    }

    override suspend fun delete(id: EntityId<Bank>): Boolean = withDefault {
        val bank = find(id) ?: return@withDefault false
        bank.cacheBacks.forEach {
            cacheBackCodeDao.deleteBy(it.id.value)
        }
        cacheBackDao.deleteBy(id.value)
        cacheBackBankDao.delete(bank.toEntity())
        true
    }

    private suspend fun cacheBackEntityList(cacheBackBankId: String) =
        cacheBackDao.list(cacheBackBankId).mapPersistent {
            it.toDomain(cacheBackCodeEntityList(it.id))
        }

    private suspend fun cacheBackCodeEntityList(cacheBackId: String) =
        cacheBackCodeDao.list(cacheBackId).mapPersistent {
            it.toDomain()
        }
}
