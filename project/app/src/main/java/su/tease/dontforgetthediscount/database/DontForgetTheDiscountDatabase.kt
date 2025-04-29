package su.tease.dontforgetthediscount.database

import androidx.room.Database
import androidx.room.RoomDatabase
import su.tease.project.feature.cacheback.data.dao.BankDao
import su.tease.project.feature.cacheback.data.dao.CacheBackCodeDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.DictionaryDao
import su.tease.project.feature.cacheback.data.dao.entity.BankEntity
import su.tease.project.feature.cacheback.data.dao.entity.BankPresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodeEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodePresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackPresetEntity

@Database(
    entities = [
        CacheBackEntity::class, BankEntity::class, CacheBackCodeEntity::class,
        BankPresetEntity::class, CacheBackPresetEntity::class,CacheBackCodePresetEntity::class,
    ],
    version = 4,
)
abstract class DontForgetTheDiscountDatabase : RoomDatabase() {
    abstract fun bankDao(): BankDao
    abstract fun cacheBackDao(): CacheBackDao
    abstract fun cacheBackCodeDao(): CacheBackCodeDao
    abstract fun dictionaryDao(): DictionaryDao
}
