package su.tease.project.feature.cashback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CashBackEntity")
data class CashBackEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "presetId")
    val presetId: String,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "ownerId")
    val ownerId: String,
)
