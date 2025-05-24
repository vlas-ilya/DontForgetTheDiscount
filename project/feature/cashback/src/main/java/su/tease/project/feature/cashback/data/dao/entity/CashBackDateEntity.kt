package su.tease.project.feature.cashback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "CashBackEntity")
class CashBackDateEntity(

    @ColumnInfo(name = "cashBackMonth")
    val cashBackMonth: Int,

    @ColumnInfo(name = "cashBackYear")
    val cashBackYear: Int,
)
