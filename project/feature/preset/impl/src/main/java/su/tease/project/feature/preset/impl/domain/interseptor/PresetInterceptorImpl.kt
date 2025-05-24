package su.tease.project.feature.preset.impl.domain.interseptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.api.domain.entity.BankIconPreset
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.api.domain.entity.MccCodePreset
import su.tease.project.feature.preset.api.domain.interceptor.PresetInterceptor
import su.tease.project.feature.preset.impl.domain.repository.PresetRepository

class PresetInterceptorImpl(
    private val repository: PresetRepository
) : PresetInterceptor {

    override suspend fun bankPresets(): PersistentList<BankPreset> =
        withDefault { repository.banks() }

    override suspend fun bankIconPresets(): PersistentList<BankIconPreset> =
        withDefault { repository.bankIcons() }

    override suspend fun cashBackPresets(bankPresetId: String): PersistentList<CashBackPreset> =
        withDefault { repository.cashBacks(bankPresetId) }

    override suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset> =
        withDefault { repository.cashBacksIcons() }

    override suspend fun mccCodePresets(): PersistentList<MccCodePreset> =
        withDefault { repository.mccCodes() }

    override suspend fun bankPreset(bankPresetId: String) =
        withDefault { repository.bankPreset(bankPresetId) }

    override suspend fun cashBackPreset(cashBackPresetId: String) =
        withDefault { repository.cashBackPreset(cashBackPresetId) }

    override suspend fun save(bankPreset: BankPreset) =
        withDefault { repository.save(bankPreset) }

    override suspend fun save(cashBackPreset: CashBackPreset) =
        withDefault { repository.save(cashBackPreset) }

    override suspend fun save(mccCodePreset: MccCodePreset) =
        withDefault { repository.save(mccCodePreset) }
}
