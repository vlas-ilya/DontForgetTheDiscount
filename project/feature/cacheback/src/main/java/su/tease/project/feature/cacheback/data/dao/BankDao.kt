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

    @Upsert
    suspend fun save(cacheBackBank: BankEntity)

    @Delete
    suspend fun delete(entity: BankEntity)
}
