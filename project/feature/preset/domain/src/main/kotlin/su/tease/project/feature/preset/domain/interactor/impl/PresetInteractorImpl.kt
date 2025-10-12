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

    override suspend fun bankPresets(presetIds: List<String>) =
        withDefault { repository.bankPresets(presetIds) }

    override suspend fun bankIconPresets(): PersistentList<BankIconPreset> =
        withDefault { repository.bankIcons() }

    override suspend fun shopPresets(): PersistentList<ShopPreset> =
        withDefault { repository.shopPresets() }

    override suspend fun shopPresets(presetIds: List<String>) =
        withDefault { repository.shopPresets(presetIds) }

    override suspend fun shopIconPresets(): PersistentList<ShopIconPreset> =
        withDefault { repository.shopIconPresets() }

    override suspend fun cashBackPresets(): PersistentList<CashBackPreset> =
        withDefault { repository.cashBackPresets() }

    override suspend fun cashBackPresets(presetId: String): PersistentList<CashBackPreset> =
        withDefault { repository.cashBackPresets(presetId) }

    override suspend fun cashBackPresets(presetIds: List<String>) =
        withDefault { repository.cashBackPresets(presetIds) }

    override suspend fun cashBacksIconPresets(): PersistentList<CashBackIconPreset> =
        withDefault { repository.cashBacksIconPresets() }

    override suspend fun mccCodePresets(): PersistentList<MccCodePreset> =
        withDefault { repository.mccCodePresets() }

    override suspend fun bankPreset(presetId: String) =
        withDefault { repository.bankPreset(presetId) }

    override suspend fun shopPreset(presetId: String) =
        withDefault { repository.shopPreset(presetId) }

    override suspend fun cashBackPreset(presetId: String) =
        withDefault { repository.cashBackPreset(presetId) }

    override suspend fun save(preset: BankPreset) =
        withDefault { repository.save(preset) }

    override suspend fun save(preset: ShopPreset) {
        withDefault { repository.save(preset) }
    }

    override suspend fun save(preset: CashBackPreset) =
        withDefault { repository.save(preset) }

    override suspend fun save(preset: MccCodePreset) =
        withDefault { repository.save(preset) }
}
