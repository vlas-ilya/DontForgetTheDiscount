package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cacheback.data.dao.entity.BankPresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodePresetEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackPresetEntity

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM BankPresetEntity")
    suspend fun banks(): List<BankPresetEntity>

    @Query("SELECT * FROM BankPresetEntity")
    suspend fun cacheBacks(): List<CacheBackPresetEntity>

    @Insert
    suspend fun add(bank: BankPresetEntity)

    @Insert
    suspend fun add(cacheBank: CacheBackPresetEntity)

    @Query("SELECT * FROM CacheBackCodePresetEntity ORDER BY usageCount ASC")
    fun cacheBackCodes(): List<CacheBackCodePresetEntity>

    @Upsert
    fun save(code: CacheBackCodePresetEntity)
}
