package su.tease.project.feature.preset.domain.interactor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset

@Suppress("TooManyFunctions")
interface PresetInteractor {

    suspend fun bankPresets(): PersistentList<BankPreset>

    suspend fun bankPresets(bankPresetIds: List<String>): PersistentList<BankPreset>

    suspend fun bankIconPresets(): PersistentList<BankIconPreset>

    suspend fun shopPresets(): PersistentList<ShopPreset>

    suspend fun shopPresets(shopPresetIds: List<String>): PersistentList<ShopPreset>

    suspend fun shopIconPresets(): PersistentList<ShopIconPreset>

    suspend fun cashBackPresets(bankPresetId: String): PersistentList<CashBackPreset>

    suspend fun cashBackPresets(cashBackPresetIds: List<String>): PersistentList<CashBackPreset>

    suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset>

    suspend fun mccCodePresets(): PersistentList<MccCodePreset>

    suspend fun bankPreset(bankPresetId: String): BankPreset

    suspend fun shopPreset(shopPresetId: String): ShopPreset

    suspend fun cashBackPreset(cashBackPresetId: String): CashBackPreset

    suspend fun save(cashBackOwnerPreset: BankPreset)

    suspend fun save(cashBackPreset: CashBackPreset)

    suspend fun save(mccCodePreset: MccCodePreset)
}
