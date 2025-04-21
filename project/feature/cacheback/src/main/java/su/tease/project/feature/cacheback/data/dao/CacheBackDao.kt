package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity

@Dao
interface CacheBackDao {

    @Query("SELECT * FROM CacheBackEntity WHERE cacheBackBankId = :cacheBackBankId")
    suspend fun list(cacheBackBankId: String): List<CacheBackEntity>

    @Insert
    suspend fun insert(cacheBackList: List<CacheBackEntity>)

    @Query("DELETE FROM CacheBackEntity WHERE cacheBackBankId = :cacheBackBankId")
    suspend fun deleteBy(cacheBackBankId: String)

    suspend fun updateBy(cacheBackBankId: String, cacheBackList: List<CacheBackEntity>) {
        deleteBy(cacheBackBankId)
        insert(cacheBackList)
    }
}
