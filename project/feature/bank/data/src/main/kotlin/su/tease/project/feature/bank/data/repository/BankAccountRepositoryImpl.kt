package su.tease.project.feature.bank.data.repository

import android.database.sqlite.SQLiteException
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.bank.data.dao.BankAccountDao
import su.tease.project.feature.bank.data.dependencies.CashBackIntegrationInteractor
import su.tease.project.feature.bank.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.bank.data.repository.exception.RepositoryException
import su.tease.project.feature.bank.data.repository.mapper.toDomain
import su.tease.project.feature.bank.data.repository.mapper.toEntity
import su.tease.project.feature.bank.domain.entity.BankAccount
import su.tease.project.feature.bank.domain.entity.CashBackDate
import su.tease.project.feature.bank.domain.repository.BankAccountRepository
import timber.log.Timber

class BankAccountRepositoryImpl(
    private val dao: BankAccountDao,
    private val cashBackIntegrationInteractor: CashBackIntegrationInteractor,
    private val presetIntegrationInteractor: PresetIntegrationInteractor,
) : BankAccountRepository {

    override suspend fun save(bankAccount: BankAccount) = withDefault {
        try {
            dao.findById(bankAccount.id)
                ?.run { cashBackIntegrationInteractor.removeForOwnerId(id) }
            dao.save(bankAccount.toEntity())
            bankAccount.cashBacks.forEach { cashBackIntegrationInteractor.save(it, bankAccount.id) }
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun get(id: String): BankAccount = withDefault {
        try {
            val bankAccountDto = dao.findById(id) ?: throw RepositoryException()
            val bankPreset = presetIntegrationInteractor.get(bankAccountDto.presetId)
            val cashBacks = cashBackIntegrationInteractor.listForOwner(bankAccountDto.id)
            bankAccountDto.toDomain(bankPreset, cashBacks)
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun list() = withDefault {
        try {
            val bankAccountDtos = dao.list()
            val bankAccountIds = bankAccountDtos.map { it.id }
            val cashBacks = cashBackIntegrationInteractor.listForOwners(bankAccountIds)
            val bankPresetIds = bankAccountDtos.map { it.presetId }
            val bankPresets = presetIntegrationInteractor.list(bankPresetIds).associateBy { it.id }
            bankAccountDtos.mapNotNull {
                it.toDomain(
                    bankPreset = bankPresets[it.presetId] ?: return@mapNotNull null,
                    cashBacks = cashBacks[it.id] ?: persistentListOf()
                )
            }.toPersistentList()
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }

    override suspend fun filterBy(date: CashBackDate) = try {
        val cashBacks = cashBackIntegrationInteractor.listForCashBackDate(date)
        val bankAccountIds = cashBacks.keys
        val bankAccountDtos = dao.listByIds(bankAccountIds)
        val bankPresetIds = bankAccountDtos.map { it.presetId }
        val bankPresets = presetIntegrationInteractor.list(bankPresetIds).associateBy { it.id }
        bankAccountDtos.mapNotNull {
            it.toDomain(
                bankPreset = bankPresets[it.presetId] ?: return@mapNotNull null,
                cashBacks = cashBacks[it.id] ?: persistentListOf()
            )
        }.toPersistentList()
    } catch (e: SQLiteException) {
        Timber.Forest.e(e)
        throw RepositoryException()
    }

    override suspend fun listDates(): PersistentList<CashBackDate> = try {
        cashBackIntegrationInteractor.listDates()
    } catch (e: SQLiteException) {
        Timber.Forest.e(e)
        throw RepositoryException()
    }

    override suspend fun delete(id: String): Boolean = withDefault {
        try {
            dao.findById(id)?.run {
                cashBackIntegrationInteractor.removeForOwnerId(id)
                dao.delete(id)
                true
            } ?: false
        } catch (e: SQLiteException) {
            Timber.Forest.e(e)
            throw RepositoryException()
        }
    }
}
