package su.tease.project.feature.cashback.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cashback.data.dao.entity.CashBackDateEntity
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity

@Dao
interface CashBackDao {
    @Upsert
    suspend fun save(it: CashBackEntity)

    @Query("DELETE FROM CashBackEntity where id = :id")
    suspend fun remove(id: String)

    @Query(
        """
            SELECT cbe.cashBackMonth as `cashBackMonth`, cbe.cashBackYear as `cashBackYear`
            FROM CashBackEntity as cbe
            GROUP BY cbe.cashBackMonth, cbe.cashBackYear
        """
    )
    suspend fun dates(): List<CashBackDateEntity>

    @Query("SELECT * FROM CashBackEntity WHERE bankAccountId = :bankAccountId")
    suspend fun filterBy(bankAccountId: String): List<CashBackEntity>

    @Query(
        """
       SELECT * 
        FROM CashBackEntity 
        WHERE bankAccountId = :bankAccountId
          AND cashBackMonth = :cashBackMonth
          AND cashBackYear = :cashBackYear
    """
    )
    suspend fun filterBy(
        bankAccountId: String,
        cashBackMonth: Int,
        cashBackYear: Int
    ): List<CashBackEntity>
}
