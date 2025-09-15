package su.tease.project.feature.cashback.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.cashback.data.dao.entity.CashBackDateEntity
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.interactor.CashBackOwnerId

@Dao
interface CashBackDao {

    @Query(
        """
            SELECT cbe.month as `month`, cbe.year as `year`
            FROM CashBackEntity as cbe
            GROUP BY cbe.month, cbe.year
        """
    )
    suspend fun listDates(): List<CashBackDateEntity>

    @Query("SELECT * from CashBackEntity WHERE ownerId = :ownerId")
    suspend fun listByOwnerId(ownerId: CashBackOwnerId): List<CashBackEntity>

    @Query("SELECT * from CashBackEntity WHERE ownerId in (:ownerIds)")
    suspend fun listByOwnerIds(ownerIds: List<CashBackOwnerId>): List<CashBackEntity>

    suspend fun listByDate(date: CashBackDate): List<CashBackEntity> =
        listByDate(date.month, date.year)

    @Query("SELECT * from CashBackEntity WHERE month = :month AND year = :year")
    suspend fun listByDate(month: Int, year: Int): List<CashBackEntity>

    @Upsert
    fun save(entity: CashBackEntity)

    @Query("DELETE FROM CashBackEntity where ownerId = :ownerId")
    fun removeByOwnerId(ownerId: CashBackOwnerId)
}
