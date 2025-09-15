package su.tease.project.feature.preset.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CashBackOwnerIconPresetEntity")
data class CashBackOwnerIconPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,

    @ColumnInfo(name = "type")
    val type: String
)
