package su.tease.dontforgetthediscount.database

import androidx.room.Database
import androidx.room.RoomDatabase
import su.tease.project.feature.cacheback.data.dao.BankAccountDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.entity.BankAccountEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.preset.impl.data.dao.PresetDao
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.PresetsVersionEntity

@Database(
    entities = [
        BankIconPresetEntity::class, BankPresetEntity::class,
        CacheBackIconPresetEntity::class, CacheBackPresetEntity::class,
        MccCodePresetEntity::class, CacheBackPresetToMccCodePresetEntity::class,
        BankAccountEntity::class, CacheBackEntity::class,
        PresetsVersionEntity::class
    ],
    version = 3,
)
abstract class DontForgetTheDiscountDatabase : RoomDatabase() {
    abstract fun bankDao(): BankAccountDao
    abstract fun cacheBackDao(): CacheBackDao
    abstract fun presetDao(): PresetDao
}
