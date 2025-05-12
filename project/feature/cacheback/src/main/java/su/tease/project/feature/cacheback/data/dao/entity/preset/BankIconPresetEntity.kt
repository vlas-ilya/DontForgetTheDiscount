package su.tease.project.feature.cacheback.data.dao.entity.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BankIconPresetEntity")
data class BankIconPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,
)
