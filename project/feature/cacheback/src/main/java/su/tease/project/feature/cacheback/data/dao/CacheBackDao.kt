package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackDateEntity
import su.tease.project.feature.cacheback.data.dao.entity.CacheBackEntity

@Dao
interface CacheBackDao {
    @Upsert
    suspend fun save(it: CacheBackEntity)

    @Query("DELETE FROM CacheBackEntity where id = :id")
    suspend fun remove(id: String)

    @Query(
        """
            SELECT cbe.cacheBackMonth as `cacheBackMonth`, cbe.cacheBackYear as `cacheBackYear`
            FROM CacheBackEntity as cbe
            GROUP BY cbe.cacheBackMonth, cbe.cacheBackYear
        """
    )
    suspend fun dates(): List<CacheBackDateEntity>

    @Query("SELECT * FROM CacheBackEntity WHERE bankAccountId = :bankAccountId")
    suspend fun filterBy(bankAccountId: String): List<CacheBackEntity>

    @Query(
        """
       SELECT * 
        FROM CacheBackEntity 
        WHERE bankAccountId = :bankAccountId
          AND cacheBackMonth = :cacheBackMonth
          AND cacheBackYear = :cacheBackYear
    """
    )
    suspend fun filterBy(
        bankAccountId: String,
        cacheBackMonth: Int,
        cacheBackYear: Int
    ): List<CacheBackEntity>
}
