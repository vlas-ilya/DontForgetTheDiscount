package su.tease.project.feature.cacheback.domain.entity.preset.mapper

import su.tease.project.feature.cacheback.domain.entity.Bank
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset

fun Bank.toPreset() = BankPreset(
    id = id.value,
    name = name.value,
    icon = icon.toPreset()
)
