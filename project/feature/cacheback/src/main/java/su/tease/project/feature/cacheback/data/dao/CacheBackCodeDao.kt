package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackCodeEntity

@Dao
interface CacheBackCodeDao {

    @Query("SELECT * FROM CacheBackCodeEntity WHERE cacheBackId = :cacheBackId")
    suspend fun list(cacheBackId: String): List<CacheBackCodeEntity>

    @Insert
    suspend fun insert(cacheBackCodeList: List<CacheBackCodeEntity>)

    @Query("DELETE FROM CacheBackCodeEntity WHERE cacheBackId = :cacheBackId")
    suspend fun deleteBy(cacheBackId: String)

    suspend fun updateBy(cacheBackId: String, cacheBackCodeList: List<CacheBackCodeEntity>) {
        deleteBy(cacheBackId)
        insert(cacheBackCodeList)
    }
}
