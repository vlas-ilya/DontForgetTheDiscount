package su.tease.project.feature.cashback.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cashback.data.dao.entity.BankAccountEntity

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
            INNER JOIN CashBackEntity AS cbe ON bae.id = cbe.bankAccountId
            WHERE cbe.cashBackMonth = :cashBackMonth AND cbe.cashBackYear = :cashBackYear
        """
    )
    suspend fun filterBy(cashBackMonth: Int, cashBackYear: Int): List<BankAccountEntity>

    @Query("DELETE FROM BankAccountEntity where id = :id")
    suspend fun delete(id: String)
}
