package su.tease.project.feature.preset.domain.entity

abstract class CashBackOwnerIconPreset(
    override val id: String,
    override val iconUrl: String,
) : IconPreset(id, iconUrl)
