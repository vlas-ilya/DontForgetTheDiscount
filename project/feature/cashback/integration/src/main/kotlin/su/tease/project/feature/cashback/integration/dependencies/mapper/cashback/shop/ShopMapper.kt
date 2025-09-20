package su.tease.project.feature.cashback.integration.dependencies.mapper.cashback.shop


import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.CashBackOwner
import su.tease.project.feature.cashback.domain.entity.preset.CashBackIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerIconPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackOwnerPreset
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset
import su.tease.project.feature.cashback.domain.entity.preset.MccCodePreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset
import su.tease.project.feature.shop.domain.entity.Shop as ExternalShop
import su.tease.project.feature.shop.domain.entity.ShopIconPreset as ExternalShopIconPreset
import su.tease.project.feature.shop.domain.entity.ShopPreset as ExternalShopPreset
import su.tease.project.feature.shop.domain.entity.CashBack as ExternalShopCashBack
import su.tease.project.feature.shop.domain.entity.CashBackDate as ExternalShopCashBackDate
import su.tease.project.feature.shop.domain.entity.CashBackIconPreset as ExternalShopCashBackIconPreset
import su.tease.project.feature.shop.domain.entity.CashBackPreset as ExternalShopCashBackPreset
import su.tease.project.feature.shop.domain.entity.MccCodePreset as ExternalShopMccCodePreset

fun ExternalShop.toDomain() = CashBackOwner(
    id = id,
    preset = preset.toDomain(),
    customName = customName,
    cashBacks = cashBacks.mapPersistent { it.toDomain(id) },
)

fun ExternalShopPreset.toDomain() = CashBackOwnerPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toDomain(),
)

fun ExternalShopIconPreset.toDomain() = CashBackOwnerIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ExternalShopCashBack.toDomain(ownerId: String) = CashBack(
    id = id,
    preset = preset.toDomain(),
    size = size,
    date = date.toDomain(),
    ownerId = ownerId,
)

fun ExternalShopCashBackPreset.toDomain() = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    mccCodes = mccCodes.mapPersistent { it.toDomain() },
)

fun ExternalShopCashBackIconPreset.toDomain() = CashBackIconPreset(
    id = id,
    iconUrl = iconUrl,
)

fun ExternalShopMccCodePreset.toDomain() = MccCodePreset(
    id = id,
    code = code,
    name = name,
    info = info,
)

fun ExternalShopCashBackDate.toDomain() = CashBackDate(
    month = month,
    year = year,
)

fun CashBackOwnerPreset.toExternalShopPreset() = ShopPreset(
    id = id,
    name = name,
    iconPreset = iconPreset.toExternal(),
)

fun CashBackOwnerIconPreset.toExternal() = ShopIconPreset(
    id = id,
    iconUrl = iconUrl,
)
