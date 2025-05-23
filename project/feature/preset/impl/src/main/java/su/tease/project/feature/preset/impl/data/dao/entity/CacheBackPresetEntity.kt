package su.tease.project.feature.preset.impl.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackPresetEntity")
data class CacheBackPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "info")
    val info: String,

    @ColumnInfo(name = "iconPresetId")
    val iconPresetId: String,

    @ColumnInfo(name = "bankPresetId")
    val bankPresetId: String,
)
