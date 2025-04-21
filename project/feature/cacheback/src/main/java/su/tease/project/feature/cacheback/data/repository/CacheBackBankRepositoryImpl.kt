package su.tease.project.feature.cacheback.data.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.entity.EntityId
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.data.dao.CacheBackBankDao
import su.tease.project.feature.cacheback.data.dao.CacheBackCodeDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.mapper.toDomain
import su.tease.project.feature.cacheback.data.dao.mapper.toDto
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank
import su.tease.project.feature.cacheback.domain.repository.CacheBackBankRepository

class CacheBackBankRepositoryImpl(
    private val cacheBackBankDao: CacheBackBankDao,
    private val cacheBackDao: CacheBackDao,
    private val cacheBackCodeDao: CacheBackCodeDao,
) : CacheBackBankRepository {

    override suspend fun save(bank: CacheBackBank) = withDefault {
        cacheBackBankDao.save(bank.toDto())
        cacheBackDao.updateBy(bank.id.value, bank.cacheBacks.map { it.toDto(bank.id) })
        bank.cacheBacks.forEach { cacheBack ->
            cacheBackCodeDao.updateBy(
                cacheBack.id.value,
                cacheBack.codes.map { it.toDto(cacheBack.id) })
        }
    }

    override suspend fun find(id: EntityId<CacheBackBank>): CacheBackBank? = withDefault {
        cacheBackBankDao.get(id.value)?.toDomain(
            cacheBackEntityList(id.value)
        )
    }

    override suspend fun list(): PersistentList<CacheBackBank> = withDefault {
        cacheBackBankDao.list().mapPersistent {
            it.toDomain(cacheBackEntityList(it.id))
        }
    }

    override suspend fun delete(id: EntityId<CacheBackBank>): Boolean= withDefault {
        val bank = find(id) ?: return@withDefault false
        bank.cacheBacks.forEach {
            cacheBackCodeDao.deleteBy(it.id.value)
        }
        cacheBackDao.deleteBy(id.value)
        cacheBackBankDao.delete(bank.toDto())
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
