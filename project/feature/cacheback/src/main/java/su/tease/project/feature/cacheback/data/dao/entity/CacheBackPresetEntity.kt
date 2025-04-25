package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackPresetEntity")
data class CacheBackPresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,
)
