package su.tease.dontforgetthediscount.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import su.tease.project.feature.cashback.data.dao.BankAccountDao
import su.tease.project.feature.cashback.data.dao.CashBackDao
import su.tease.project.feature.cashback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.preset.impl.data.dao.PresetDao
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.PresetsVersionEntity

@Database(
    entities = [
        BankIconPresetEntity::class, BankPresetEntity::class,
        CashBackIconPresetEntity::class, CashBackPresetEntity::class,
        MccCodePresetEntity::class, CashBackPresetToMccCodePresetEntity::class,
        BankAccountEntity::class, CashBackEntity::class,
        PresetsVersionEntity::class
    ],
    version = 2,
    exportSchema = false,
)
abstract class DontForgetTheDiscountDatabase : RoomDatabase() {
    abstract fun bankDao(): BankAccountDao
    abstract fun cashBackDao(): CashBackDao
    abstract fun presetDao(): PresetDao
}
