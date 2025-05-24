package su.tease.project.feature.cashback.data.dao.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.data.dao.CashBackDao
import su.tease.project.feature.cashback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor

internal class MapperHelper(
    private val cashBackDao: CashBackDao,
    private val presetInterceptor: PresetInterceptor,
) {
    suspend fun BankAccountEntity.toDomain() =
        toDomain(
            getBankPreset = { getBankPreset(it) },
            getCashBacks = { getCashBacks(it) }
        )

    suspend fun BankAccountEntity.toDomain(cashBackMonth: Int, cashBackYear: Int) =
        toDomain(
            getBankPreset = { getBankPreset(it) },
            getCashBacks = { getCashBacks(it, cashBackMonth, cashBackYear) }
        )

    private suspend fun getBankPreset(bankPresetId: String) = presetInterceptor
        .bankPreset(bankPresetId)

    private suspend fun getCashBacks(bankAccountId: String) = cashBackDao
        .filterBy(bankAccountId)
        .mapPersistent { it.toDomain() }

    private suspend fun getCashBacks(
        bankAccountId: String,
        cashBackMonth: Int,
        cashBackYear: Int
    ) = cashBackDao
        .filterBy(bankAccountId, cashBackMonth, cashBackYear)
        .mapPersistent { it.toDomain() }

    private suspend fun CashBackEntity.toDomain() =
        toDomain(getCashBackPreset = { getCashBackPreset(it) })

    private suspend fun getCashBackPreset(cashBackPresetId: String) = presetInterceptor
        .cashBackPreset(cashBackPresetId)
}
