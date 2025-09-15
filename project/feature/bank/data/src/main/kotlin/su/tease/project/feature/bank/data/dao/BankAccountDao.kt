package su.tease.project.feature.bank.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.bank.data.dao.entity.BankAccountEntity

@Dao
interface BankAccountDao {

    @Query("SELECT * FROM BankAccountEntity where id = :id")
    suspend fun findById(id: String): BankAccountEntity?

    @Upsert
    suspend fun save(entity: BankAccountEntity)

    @Query("SELECT * FROM BankAccountEntity")
    suspend fun list(): List<BankAccountEntity>

    @Query("SELECT DISTINCT * FROM BankAccountEntity WHERE id in (:ids)")
    suspend fun listByIds(ids: Collection<String>): List<BankAccountEntity>

    @Query("DELETE FROM BankAccountEntity where id = :id")
    suspend fun delete(id: String)
}
