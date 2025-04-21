package su.tease.dontforgetthediscount.database

import androidx.room.Database
import androidx.room.RoomDatabase
import su.tease.project.feature.cacheback.data.dao.CacheBackBankDao
import su.tease.project.feature.cacheback.data.dao.CacheBackCodeDao
import su.tease.project.feature.cacheback.data.dao.CacheBackDao
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackBankEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodeEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity

@Database(
    entities = [
        CacheBackEntity::class, CacheBackBankEntity::class, CacheBackCodeEntity::class,
    ],
    version = 2,
)
abstract class DontForgetTheDiscountDatabase : RoomDatabase() {
    abstract fun cacheBackBankDao(): CacheBackBankDao
    abstract fun cacheBackDao(): CacheBackDao
    abstract fun cacheBackCodeDao(): CacheBackCodeDao
}
