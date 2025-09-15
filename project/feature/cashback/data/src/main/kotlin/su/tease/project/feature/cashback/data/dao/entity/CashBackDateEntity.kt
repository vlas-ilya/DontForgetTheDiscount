package su.tease.project.feature.cashback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "CashBackEntity")
class CashBackDateEntity(

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "year")
    val year: Int,
)
