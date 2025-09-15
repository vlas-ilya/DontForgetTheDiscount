package su.tease.project.feature.shop.data.repository

import android.database.sqlite.SQLiteException
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.shop.data.dao.ShopDao
import su.tease.project.feature.shop.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.shop.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.shop.data.repository.exception.RepositoryException
import su.tease.project.feature.shop.data.repository.mapper.toDomain
import su.tease.project.feature.shop.data.repository.mapper.toEntity
import su.tease.project.feature.shop.domain.entity.CashBackDate
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.repository.ShopRepository
import timber.log.Timber

class ShopRepositoryImpl(
    private val shopDao: ShopDao,
    private val cashBackIntegrationInteractor: CashBackIntegrationInteractor,
    private val presetIntegrationInteractor: PresetIntegrationInteractor,
) : ShopRepository {

    override suspend fun save(value: Shop) = withDefault {
        try {
            shopDao.findById(value.id)
                ?.run { cashBackIntegrationInteractor.removeForOwnerId(id) }
            shopDao.save(value.toEntity())
            value.cashBacks.forEach { cashBackIntegrationInteractor.save(it, value.id) }
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun get(id: String): Shop = withDefault {
        try {
            val shopDto = shopDao.findById(id) ?: throw RepositoryException()
            val shopPreset = presetIntegrationInteractor.get(shopDto.presetId)
            val cashBacks = cashBackIntegrationInteractor.listForOwner(shopDto.id)
            shopDto.toDomain(shopPreset, cashBacks)
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun list() = withDefault {
        try {
            val shopDtos = shopDao.list()
            val shopIds = shopDtos.map { it.id }
            val cashBacks = cashBackIntegrationInteractor.listForOwners(shopIds)
            val presetIds = shopDtos.map { it.presetId }
            val presets = presetIntegrationInteractor.list(presetIds).associateBy { it.id }
            shopDtos.mapNotNull {
                it.toDomain(
                    shopPreset = presets[it.presetId] ?: return@mapNotNull null,
                    cashBacks = cashBacks[it.id] ?: persistentListOf()
                )
            }.toPersistentList()
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun filterBy(date: CashBackDate) =
        try {
            val cashBacks = cashBackIntegrationInteractor.listForCashBackDate(date)
            val shopIds = cashBacks.keys
            val shopDtos = shopDao.listByIds(shopIds)
            val presetIds = shopDtos.map { it.presetId }
            val presets = presetIntegrationInteractor.list(presetIds).associateBy { it.id }
            shopDtos.mapNotNull {
                it.toDomain(
                    shopPreset = presets[it.presetId] ?: return@mapNotNull null,
                    cashBacks = cashBacks[it.id] ?: persistentListOf()
                )
            }.toPersistentList()
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }

    override suspend fun filterBy(ids: List<String>) =
        try {
            val shopDtos = shopDao.listByIds(ids)
            val shopIds = shopDtos.map { it.id }
            val cashBacks = cashBackIntegrationInteractor.listForOwners(shopIds)
            val presetIds = shopDtos.map { it.presetId }
            val presets = presetIntegrationInteractor.list(presetIds).associateBy { it.id }
            shopDtos.mapNotNull {
                it.toDomain(
                    shopPreset = presets[it.presetId] ?: return@mapNotNull null,
                    cashBacks = cashBacks[it.id] ?: persistentListOf()
                )
            }.toPersistentList()
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }

    override suspend fun listDates(): PersistentList<CashBackDate> =
        try {
            cashBackIntegrationInteractor.listDates()
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }

    override suspend fun delete(id: String): Boolean = withDefault {
        try {
            shopDao.findById(id)?.run {
                cashBackIntegrationInteractor.removeForOwnerId(id)
                shopDao.delete(id)
                true
            } ?: false
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }
}
