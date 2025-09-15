package su.tease.project.feature.preset.domain.entity

import kotlinx.parcelize.Parcelize

@Parcelize
data class CashBackIconPreset(
    override val id: String,
    override val iconUrl: String,
) : IconPreset(id, iconUrl)
