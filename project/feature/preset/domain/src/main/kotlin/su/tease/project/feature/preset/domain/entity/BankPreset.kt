package su.tease.project.feature.preset.domain.entity

import kotlinx.parcelize.Parcelize

@Parcelize
data class BankPreset(
    override val id: String,
    override val name: String,
    override val iconPreset: BankIconPreset,
) : CashBackOwnerPreset(id, name, iconPreset)
