package su.tease.project.feature.preset.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

@Parcelize
sealed class CashBackOwnerPreset(
    open val id: String,
    open val name: String,
    open val iconPreset: CashBackOwnerIconPreset,
) : Parcelable

enum class CashBackOwnerPresetType {
    BANK,
    SHOP,
}

val KClass<out CashBackOwnerPreset>.cashBackOwnerPresetType: CashBackOwnerPresetType
    get() = when (this.qualifiedName) {
        BankPreset::class.qualifiedName -> CashBackOwnerPresetType.BANK
        ShopPreset::class.qualifiedName -> CashBackOwnerPresetType.SHOP
        else -> error("Unsupported cash back owner preset type")
    }
