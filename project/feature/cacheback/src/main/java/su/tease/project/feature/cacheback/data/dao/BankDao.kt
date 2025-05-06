package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cacheback.data.dao.entity.BankEntity

@Dao
interface BankDao {

    @Query("SELECT * FROM BankEntity where id = :id")
    suspend fun get(id: String): BankEntity?

    @Query("SELECT * FROM BankEntity")
    suspend fun list(): List<BankEntity>

    @Query(
        """
        SELECT DISTINCT be.* 
        FROM BankEntity AS be
        INNER JOIN CacheBackEntity AS cbe ON cbe.bankId = be.id
        WHERE cbe.cacheBackMonth = :month AND cbe.cacheBackYear = :year
    """
    )
    suspend fun filterBy(month: Int, year: Int): List<BankEntity>

    @Upsert
    suspend fun save(cacheBackBank: BankEntity)

    @Delete
    suspend fun delete(entity: BankEntity)
}
