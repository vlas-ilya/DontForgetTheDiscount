package su.tease.project.feature.cacheback.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cacheback.data.dao.entity.BankAccountEntity

@Dao
interface BankAccountDao {

    @Query("SELECT * FROM BankAccountEntity where id = :id")
    suspend fun findById(id: String): BankAccountEntity?

    @Upsert
    suspend fun save(entity: BankAccountEntity)

    @Query("SELECT * FROM BankAccountEntity")
    suspend fun list(): List<BankAccountEntity>

    @Query(
        """
            SELECT DISTINCT bae.* 
            FROM BankAccountEntity AS bae
            INNER JOIN CacheBackEntity AS cbe ON bae.id = cbe.bankAccountId
            WHERE cbe.cacheBackMonth = :cacheBackMonth AND cbe.cacheBackYear = :cacheBackYear
        """
    )
    suspend fun filterBy(cacheBackMonth: Int, cacheBackYear: Int): List<BankAccountEntity>

    @Query("DELETE FROM BankAccountEntity where id = :id")
    suspend fun delete(id: String)
}
