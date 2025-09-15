package su.tease.project.feature.preset.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CashBackPresetEntity")
data class CashBackPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "info")
    val info: String,

    @ColumnInfo(name = "iconPresetId")
    val iconPresetId: String,

    @ColumnInfo(name = "cashBackOwnerPresetId")
    val cashBackOwnerPresetId: String,
)
