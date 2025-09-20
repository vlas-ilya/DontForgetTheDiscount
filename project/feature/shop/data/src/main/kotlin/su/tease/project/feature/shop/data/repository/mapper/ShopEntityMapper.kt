package su.tease.project.feature.shop.data.repository.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.shop.data.dao.entity.ShopEntity
import su.tease.project.feature.shop.domain.entity.CashBack
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.entity.ShopPreset

fun Shop.toEntity() = ShopEntity(
    id = id,
    presetId = preset.id,
    customName = customName,
)

fun ShopEntity.toDomain(
    shopPreset: ShopPreset,
    cashBacks: PersistentList<CashBack>,
) = Shop(
    id = id,
    preset = shopPreset,
    customName = customName,
    cashBacks = cashBacks,
)
