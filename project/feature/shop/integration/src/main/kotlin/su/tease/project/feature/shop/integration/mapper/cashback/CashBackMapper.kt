@file:Suppress("TooManyFunctions")

package su.tease.project.feature.shop.integration.mapper.cashback

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.shop.domain.entity.Shop
import su.tease.project.feature.shop.domain.entity.CashBack as DomainCashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate as DomainCashBackDate
import su.tease.project.feature.shop.domain.entity.CashBackIconPreset as DomainCashBackIconPreset
import su.tease.project.feature.shop.domain.entity.CashBackPreset as DomainCashBackPreset
import su.tease.project.feature.shop.domain.entity.MccCodePreset as DomainMccCodePreset
import su.tease.project.feature.shop.domain.entity.ShopIconPreset as DomainShopIconPreset
import su.tease.project.feature.shop.domain.entity.ShopPreset as DomainShopPreset

fun CashBackOwner.toDomain() = Shop(
    id = id,
    preset = preset.toDomain(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toDomain() },
)

fun CashBackOwnerPreset.toDomain() = DomainShopPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun CashBackOwnerIconPreset.toDomain() = DomainShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun Shop.toExternal() = CashBackOwner(
    id = id,
    preset = preset.toExternal(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toExternal(id) },
)

fun DomainCashBack.toExternal(ownerId: String) = CashBack(
    id = id,
    preset = preset.toExternal(),
    size = size,
    date = date.toExternal(),
    ownerId = ownerId,
)

fun DomainCashBackPreset.toExternal() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toExternal(),
    mccCodes = mccCodes.mapPersistent { it.toExternal() },
)

fun DomainCashBackIconPreset.toExternal() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainShopPreset.toExternal() = CashBackOwnerPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun DomainShopIconPreset.toExternal() = CashBackOwnerIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun DomainMccCodePreset.toExternal() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun DomainCashBackDate.toExternal() = CashBackDate(
    month = month,
    year = year,
)

fun CashBack.toDomain() = DomainCashBack(
    id = id,
    preset = preset.toDomain(),
    size = size,
    date = date.toDomain(),
)

fun CashBackPreset.toDomain() = DomainCashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    mccCodes = mccCodes.mapPersistent { it.toDomain() },
)

fun CashBackIconPreset.toDomain() = DomainCashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun MccCodePreset.toDomain() = DomainMccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun CashBackDate.toDomain() = DomainCashBackDate(
    month = month,
    year = year,
)
