package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackBankEntity

@Dao
interface CacheBackBankDao {

    @Query("SELECT * FROM CacheBackBankEntity where id = :id")
    suspend fun get(id: String): CacheBackBankEntity?

    @Query("SELECT * FROM CacheBackBankEntity")
    suspend fun list(): List<CacheBackBankEntity>

    @Upsert
    suspend fun save(cacheBackBank: CacheBackBankEntity)

    @Delete
    suspend fun delete(entity: CacheBackBankEntity)
}
