package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import su.tease.project.feature.cacheback.data.dao.entity.BankPresetEntity
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
}
