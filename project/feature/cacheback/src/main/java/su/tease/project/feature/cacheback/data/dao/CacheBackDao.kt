package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackDateEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity

@Dao
interface CacheBackDao {

    @Query("SELECT * FROM CacheBackEntity WHERE bankId = :bankId")
    suspend fun list(bankId: String): List<CacheBackEntity>

    @Query(
        """
        SELECT * 
        FROM CacheBackEntity 
        WHERE bankId = :bankId AND cacheBackMonth = :month AND cacheBackYear = :year
    """
    )
    suspend fun filterBy(bankId: String, month: Int, year: Int): List<CacheBackEntity>

    @Query(
        """
        SELECT cbe.cacheBackMonth as `cacheBackMonth`, cbe.cacheBackYear as `cacheBackYear`
        FROM CacheBackEntity as cbe
        GROUP BY cbe.cacheBackMonth, cbe.cacheBackYear
    """
    )
    suspend fun listDates(): List<CacheBackDateEntity>

    @Insert
    suspend fun insert(cacheBackList: List<CacheBackEntity>)

    @Query("DELETE FROM CacheBackEntity WHERE bankId = :bankId")
    suspend fun deleteBy(bankId: String)

    suspend fun updateBy(bankId: String, cacheBackList: List<CacheBackEntity>) {
        deleteBy(bankId)
        insert(cacheBackList)
    }
}
