package su.tease.project.feature.preset.domain.interactor.impl

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.preset.domain.repository.PresetRepository

@Suppress("TooManyFunctions")
class PresetInteractorImpl(
    private val repository: PresetRepository
) : PresetInteractor {

    override suspend fun bankPresets(): PersistentList<BankPreset> =
        withDefault { repository.bankPresets() }

    override suspend fun bankPresets(bankPresetIds: List<String>) =
        withDefault { repository.bankPresets(bankPresetIds) }

    override suspend fun bankIconPresets(): PersistentList<BankIconPreset> =
        withDefault { repository.bankIcons() }

    override suspend fun shopPresets(): PersistentList<ShopPreset> =
        withDefault { repository.shopPresets() }

    override suspend fun shopPresets(shopPresetIds: List<String>) =
        withDefault { repository.shopPresets(shopPresetIds) }

    override suspend fun shopIconPresets(): PersistentList<ShopIconPreset> =
        withDefault { repository.shopIconPresets() }

    override suspend fun cashBackPresets(bankPresetId: String): PersistentList<CashBackPreset> =
        withDefault { repository.cashBackPresets(bankPresetId) }

    override suspend fun cashBackPresets(cashBackPresetIds: List<String>) =
        withDefault { repository.cashBackPresets(cashBackPresetIds) }

    override suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset> =
        withDefault { repository.cashBacksIconPresets() }

    override suspend fun mccCodePresets(): PersistentList<MccCodePreset> =
        withDefault { repository.mccCodePresets() }

    override suspend fun bankPreset(bankPresetId: String) =
        withDefault { repository.bankPreset(bankPresetId) }

    override suspend fun shopPreset(shopPresetId: String) =
        withDefault { repository.shopPreset(shopPresetId) }

    override suspend fun cashBackPreset(cashBackPresetId: String) =
        withDefault { repository.cashBackPreset(cashBackPresetId) }

    override suspend fun save(cashBackOwnerPreset: BankPreset) =
        withDefault { repository.save(cashBackOwnerPreset) }

    override suspend fun save(cashBackPreset: CashBackPreset) =
        withDefault { repository.save(cashBackPreset) }

    override suspend fun save(mccCodePreset: MccCodePreset) =
        withDefault { repository.save(mccCodePreset) }
}
