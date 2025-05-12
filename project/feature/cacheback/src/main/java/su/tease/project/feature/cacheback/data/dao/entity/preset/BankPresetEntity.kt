package su.tease.project.feature.cacheback.data.dao.entity.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BankPresetEntity")
data class BankPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "iconPresetId")
    val iconPresetId: String,
)
