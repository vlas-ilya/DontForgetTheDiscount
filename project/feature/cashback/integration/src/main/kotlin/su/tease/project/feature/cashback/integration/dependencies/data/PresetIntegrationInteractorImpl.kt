package su.tease.project.feature.cashback.integration.dependencies.data

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset
import su.tease.project.feature.preset.domain.entity.MccCodePreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset as DomainCashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset as DomainCashBackPreset
import su.tease.project.feature.cashback.domain.entity.preset.MccCodePreset as DomainMccCodePreset

class PresetIntegrationInteractorImpl(
    private val presetInteractor: PresetInteractor,
) : PresetIntegrationInteractor {

    override suspend fun get(cashBackPresetId: String): DomainCashBackPreset {
        return presetInteractor.cashBackPreset(cashBackPresetId).toDomain()
    }

    override suspend fun list(presetIds: List<String>): List<DomainCashBackPreset> {
        val presets = presetInteractor.cashBackPresets(presetIds)
        return presets.map { it.toDomain() }
    }

    private fun CashBackPreset.toDomain() = DomainCashBackPreset(
        id = id,
        name = name,
        info = info,
        iconPreset = iconPreset.toDomain(),
        mccCodes = mccCodes.mapPersistent { it.toDomain() },
    )

    private fun CashBackIconPreset.toDomain() = DomainCashBackIconPreset(
        id = id,
        iconUrl = iconUrl,
    )

    private fun MccCodePreset.toDomain() = DomainMccCodePreset(
        id = id,
        code = code,
        name = name,
        info = info,
    )
}
