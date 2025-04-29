package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackCodePresetEntity")
data class CacheBackCodePresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "usageCount")
    val usageCount: Int,
)
