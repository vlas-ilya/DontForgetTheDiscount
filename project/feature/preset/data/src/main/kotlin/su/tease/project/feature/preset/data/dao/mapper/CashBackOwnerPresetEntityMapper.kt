package su.tease.project.feature.preset.data.dao.mapper

import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.domain.entity.BankIconPreset
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerIconPreset
import su.tease.project.feature.preset.domain.entity.CashBackOwnerPreset
import su.tease.project.feature.preset.domain.entity.ShopIconPreset
import su.tease.project.feature.preset.domain.entity.ShopPreset

inline fun CashBackOwnerPresetEntity.toDomain(
    getIcon: (iconPresetId: String) -> CashBackOwnerIconPreset
): CashBackOwnerPreset = when (type) {
    BANK_PRESET_TYPE -> BankPreset(
        id = id,
        name = name,
        iconPreset = getIcon(iconPresetId) as BankIconPreset,
    )

    SHOP_PRESET_TYPE -> ShopPreset(
        id = id,
        name = name,
        iconPreset = getIcon(iconPresetId) as ShopIconPreset,
    )
    else -> error("Unsupported cache back owner type")
}

fun BankPreset.toEntity() = CashBackOwnerPresetEntity(
    id = id,
    name = name,
    iconPresetId = iconPreset.id,
    type = BANK_PRESET_TYPE,
)

fun ShopPreset.toEntity() = CashBackOwnerPresetEntity(
    id = id,
    name = name,
    iconPresetId = iconPreset.id,
    type = SHOP_PRESET_TYPE,
)

const val BANK_PRESET_TYPE = "BANK_PRESET_TYPE"
const val SHOP_PRESET_TYPE = "SHOP_PRESET_TYPE"
