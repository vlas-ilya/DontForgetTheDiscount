package su.tease.project.feature.preset.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopPreset(
    override val id: String,
    override val name: String,
    override val iconPreset: ShopIconPreset,
) : CashBackOwnerPreset(id, name, iconPreset), Parcelable
