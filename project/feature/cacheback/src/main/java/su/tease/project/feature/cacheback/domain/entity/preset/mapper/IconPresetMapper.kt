package su.tease.project.feature.cacheback.domain.entity.preset.mapper

import su.tease.project.feature.cacheback.domain.entity.BankIcon
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

fun BankIcon.toPreset() = IconPreset(
    url = url,
)
