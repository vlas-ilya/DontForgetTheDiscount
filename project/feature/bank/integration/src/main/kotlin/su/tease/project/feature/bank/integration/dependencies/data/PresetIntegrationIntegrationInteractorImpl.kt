package su.tease.project.feature.bank.integration.dependencies.data

import su.tease.project.feature.bank.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.bank.domain.entity.BankIconPreset as DomainBankIconPreset
import su.tease.project.feature.bank.domain.entity.BankPreset as DomainBankPreset

class PresetIntegrationIntegrationInteractorImpl(
    private val presetInteractor: PresetInteractor,
) : PresetIntegrationInteractor {

    override suspend fun get(presetId: String) =
        presetInteractor.bankPreset(presetId).toDomain()

    override suspend fun list(presetIds: List<String>) =
        presetInteractor.bankPresets(presetIds).map { it.toDomain() }

    private fun BankPreset.toDomain() = DomainBankPreset(
        id = id,
        name = name,
        iconPreset = iconPreset.toDomain(),
    )

    private fun BankIconPreset.toDomain() = DomainBankIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
