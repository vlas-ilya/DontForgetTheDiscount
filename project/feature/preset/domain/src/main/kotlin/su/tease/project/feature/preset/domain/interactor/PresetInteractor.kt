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

    suspend fun bankPresets(presetIds: List<String>): PersistentList<BankPreset>

    suspend fun bankIconPresets(): PersistentList<BankIconPreset>

    suspend fun shopPresets(): PersistentList<ShopPreset>

    suspend fun shopPresets(presetIds: List<String>): PersistentList<ShopPreset>

    suspend fun shopIconPresets(): PersistentList<ShopIconPreset>

    suspend fun cashBackPresets(presetId: String): PersistentList<CashBackPreset>

    suspend fun cashBackPresets(presetIds: List<String>): PersistentList<CashBackPreset>

    suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset>

    suspend fun mccCodePresets(): PersistentList<MccCodePreset>

    suspend fun bankPreset(presetId: String): BankPreset

    suspend fun shopPreset(presetId: String): ShopPreset

    suspend fun cashBackPreset(presetId: String): CashBackPreset

    suspend fun save(preset: BankPreset)

    suspend fun save(preset: ShopPreset)

    suspend fun save(preset: CashBackPreset)

    suspend fun save(preset: MccCodePreset)
}
