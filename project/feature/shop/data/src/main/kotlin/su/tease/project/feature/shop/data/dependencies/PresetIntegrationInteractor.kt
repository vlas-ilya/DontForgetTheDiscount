package su.tease.project.feature.shop.data.dependencies

import su.tease.project.feature.shop.domain.entity.ShopPreset

interface PresetIntegrationInteractor {
    suspend fun get(presetId: String): ShopPreset
    suspend fun list(presetIds: List<String>): List<ShopPreset>
}
