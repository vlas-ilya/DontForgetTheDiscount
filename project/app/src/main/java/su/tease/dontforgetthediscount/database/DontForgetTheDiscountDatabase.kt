package su.tease.dontforgetthediscount.database

import androidx.room.Database
import androidx.room.RoomDatabase
import su.tease.project.feature.bank.data.dao.BankAccountDao
import su.tease.project.feature.bank.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cashback.data.dao.CashBackDao
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.preset.data.dao.PresetDao
import su.tease.project.feature.preset.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerIconPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.data.dao.entity.PresetsVersionEntity
import su.tease.project.feature.shop.data.dao.ShopDao
import su.tease.project.feature.shop.data.dao.entity.ShopEntity

@Database(
    entities = [
        CashBackEntity::class,
        BankAccountEntity::class,
        ShopEntity::class,
        CashBackPresetEntity::class,
        CashBackIconPresetEntity::class,
        CashBackOwnerPresetEntity::class,
        CashBackOwnerIconPresetEntity::class,
        MccCodePresetEntity::class,
        CashBackPresetToMccCodePresetEntity::class,
        PresetsVersionEntity::class
    ],
    version = 2,
    exportSchema = false,
)
abstract class DontForgetTheDiscountDatabase : RoomDatabase() {
    abstract fun cashBackDao(): CashBackDao
    abstract fun bankDao(): BankAccountDao
    abstract fun presetDao(): PresetDao
    abstract fun shopDao(): ShopDao
}
