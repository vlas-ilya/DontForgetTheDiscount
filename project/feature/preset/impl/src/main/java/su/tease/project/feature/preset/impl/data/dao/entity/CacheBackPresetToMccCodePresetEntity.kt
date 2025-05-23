package su.tease.project.feature.preset.impl.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "CacheBackPresetToMccCodePresetEntity",
    primaryKeys = ["cacheBackPresetId", "mccCodePresetId"]
)
data class CacheBackPresetToMccCodePresetEntity(

    @ColumnInfo(name = "cacheBackPresetId")
    val cacheBackPresetId: String,

    @ColumnInfo(name = "mccCodePresetId")
    val mccCodePresetId: String,
)
