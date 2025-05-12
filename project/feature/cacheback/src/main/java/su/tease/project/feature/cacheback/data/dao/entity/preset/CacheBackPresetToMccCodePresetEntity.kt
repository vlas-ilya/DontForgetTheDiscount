package su.tease.project.feature.cacheback.data.dao.entity.preset

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
