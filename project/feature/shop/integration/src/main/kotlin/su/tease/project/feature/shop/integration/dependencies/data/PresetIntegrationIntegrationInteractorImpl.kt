package su.tease.project.feature.shop.integration.dependencies.data

import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.interactor.PresetInteractor
import su.tease.project.feature.shop.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.shop.domain.entity.ShopIconPreset as DomainShopIconPreset
import su.tease.project.feature.shop.domain.entity.ShopPreset as DomainShopPreset

class PresetIntegrationIntegrationInteractorImpl(
    private val presetInteractor: PresetInteractor,
) : PresetIntegrationInteractor {

    override suspend fun get(presetId: String) =
        presetInteractor.shopPreset(presetId).toDomain()

    override suspend fun list(presetIds: List<String>) =
        presetInteractor.shopPresets(presetIds).map { it.toDomain() }

    private fun ShopPreset.toDomain() = DomainShopPreset(
        id = id,
        name = name,
        iconPreset = iconPreset.toDomain(),
    )

    private fun ShopIconPreset.toDomain() = DomainShopIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
}
