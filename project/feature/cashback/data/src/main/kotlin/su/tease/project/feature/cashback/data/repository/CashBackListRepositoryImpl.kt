package su.tease.project.feature.cashback.data.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.data.dao.CashBackDao
import su.tease.project.feature.cashback.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.cashback.data.repository.mapper.toDomain
import su.tease.project.feature.cashback.data.repository.mapper.toEntity
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId
import su.tease.project.feature.cashback.domain.repository.CashBackListRepository

class CashBackListRepositoryImpl(
    private val cashBackDao: CashBackDao,
    private val presetInteractor: PresetIntegrationInteractor,
) : CashBackListRepository {

    override suspend fun listDates() = cashBackDao.listDates().mapPersistent { it.toDomain() }

    override suspend fun listByOwnerId(ownerId: CashBackOwnerId) =
        cashBackDao.listByOwnerId(ownerId).toDomain(presetInteractor)

    override suspend fun listByOwnerIds(ownerIds: List<CashBackOwnerId>) =
        cashBackDao.listByOwnerIds(ownerIds).toDomain(presetInteractor)

    override suspend fun listByDate(date: CashBackDate): PersistentList<CashBack> =
        cashBackDao.listByDate(date).toDomain(presetInteractor)

    override suspend fun save(cashBack: CashBack) =
        cashBackDao.save(cashBack.toEntity())

    override suspend fun removeByOwnerId(ownerId: CashBackOwnerId) =
        cashBackDao.removeByOwnerId(ownerId)
}
