package su.tease.project.feature.preset.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CashBackOwnerPresetEntity")
data class CashBackOwnerPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "iconPresetId")
    val iconPresetId: String,

    @ColumnInfo(name = "type")
    val type: String,
)
