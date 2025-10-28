package su.tease.project.feature.preset.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset

interface PresetRepository :
    BankPresetRepository,
    BankIconPresetRepository,
    ShopPresetRepository,
    ShopIconPresetRepository,
    CashBackPresetRepository,
    CashBackIconPresetRepository,
    MccCodePresetRepository

interface BankPresetRepository {
    suspend fun bankPresets(): PersistentList<BankPreset>
    suspend fun bankPreset(bankPresetId: String): BankPreset
    suspend fun bankPresets(bankPresetIds: List<String>): PersistentList<BankPreset>
    suspend fun save(cashBackOwnerPreset: BankPreset)
}

interface BankIconPresetRepository {
    suspend fun bankIcons(): PersistentList<BankIconPreset>
    suspend fun save(preset: BankIconPreset)
}

interface ShopPresetRepository {
    suspend fun shopPresets(): PersistentList<ShopPreset>
    suspend fun shopPreset(shopPresetId: String): ShopPreset
    suspend fun shopPresets(shopPresetIds: List<String>): PersistentList<ShopPreset>
    suspend fun save(shopPreset: ShopPreset)
}

interface ShopIconPresetRepository {
    suspend fun shopIconPresets(): PersistentList<ShopIconPreset>
    suspend fun save(preset: ShopIconPreset)
}

interface CashBackPresetRepository {
    suspend fun cashBackPresets(): PersistentList<CashBackPreset>
    suspend fun cashBackPresets(ownerPresetId: String): PersistentList<CashBackPreset>
    suspend fun cashBackPreset(cashBackPresetId: String): CashBackPreset
    suspend fun cashBackPresets(cashBackPresetIds: List<String>): PersistentList<CashBackPreset>
    suspend fun save(cashBackPreset: CashBackPreset)
}

interface CashBackIconPresetRepository {
    suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset>
    suspend fun save(preset: CashBackIconPreset)
}

interface MccCodePresetRepository {
    suspend fun mccCodePresets(): PersistentList<MccCodePreset>
    suspend fun save(mccCodePreset: MccCodePreset)
}
