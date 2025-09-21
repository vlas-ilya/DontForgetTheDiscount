package su.tease.project.feature.shop.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.shop.data.dao.entity.ShopEntity

@Dao
interface ShopDao {

    @Query("SELECT * FROM ShopEntity where id = :id")
    suspend fun findById(id: String): ShopEntity?

    @Query("SELECT * FROM ShopEntity ORDER BY customName")
    suspend fun list(): List<ShopEntity>

    @Query("SELECT DISTINCT * FROM ShopEntity WHERE id in (:ids) ORDER BY customName")
    suspend fun listByIds(ids: Collection<String>): List<ShopEntity>

    @Upsert
    suspend fun save(entity: ShopEntity)

    @Query("DELETE FROM ShopEntity where id = :id")
    suspend fun delete(id: String)
}
