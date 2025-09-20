package su.tease.project.feature.shop.integration.mapper.preset

import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.preset.domain.entity.CashBackIconPreset
import su.tease.project.feature.shop.domain.entity.ShopIconPreset as DomainShopIconPreset
import su.tease.project.feature.shop.domain.entity.ShopPreset as DomainShopPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset as DomainCashBackIconPreset

fun ShopPreset.toDomain() = DomainShopPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun ShopIconPreset.toDomain() = DomainShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainShopPreset.toExternal() = ShopPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun DomainShopIconPreset.toExternal() = ShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun CashBackOwnerIconPreset.toPreset() = ShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainCashBackIconPreset.toPreset() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)
