package su.tease.project.feature.preset.data.dao.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerIconPresetEntity
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset

fun CashBackOwnerIconPresetEntity.toDomain() = when (type) {
    BANK_ICON_PRESET_TYPE -> BankIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
    SHOP_ICON_PRESET_TYPE -> ShopIconPreset(
        id = id,
        iconUrl = iconUrl,
    )
    else -> error("Unsupported cache back owner icon type")
}

fun BankIconPreset.toEntity() = CashBackOwnerIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
    type = "BANK_ICON_PRESET_TYPE"
)

fun ShopIconPreset.toEntity() = CashBackOwnerIconPresetEntity(
    id = id,
    iconUrl = iconUrl,
    type = SHOP_ICON_PRESET_TYPE,
)

const val BANK_ICON_PRESET_TYPE = "BANK_ICON_PRESET_TYPE"
const val SHOP_ICON_PRESET_TYPE = "SHOP_ICON_PRESET_TYPE"
