package su.tease.project.feature.cashback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CashBackEntity")
data class CashBackEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "cashBackPresetId")
    val cashBackPresetId: String,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "cashBackMonth")
    val cashBackMonth: Int,

    @ColumnInfo(name = "cashBackYear")
    val cashBackYear: Int,

    @ColumnInfo(name = "bankAccountId")
    val bankAccountId: String,
)
