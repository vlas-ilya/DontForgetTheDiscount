package su.tease.project.feature.preset.domain.entity

import kotlinx.parcelize.Parcelize

@Parcelize
data class BankIconPreset(
    override val id: String,
    override val iconUrl: String,
) : CashBackOwnerIconPreset(id, iconUrl)
