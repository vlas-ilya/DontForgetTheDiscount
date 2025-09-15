package su.tease.project.feature.preset.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "CashBackPresetToMccCodePresetEntity",
    primaryKeys = ["cashBackPresetId", "mccCodePresetId"],
)
data class CashBackPresetToMccCodePresetEntity(

    @ColumnInfo(name = "cashBackPresetId")
    val cashBackPresetId: String,

    @ColumnInfo(name = "mccCodePresetId")
    val mccCodePresetId: String,
)
