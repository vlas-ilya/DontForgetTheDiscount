package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackEntity")
data class CacheBackEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "info")
    val info: String,

    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "bankId")
    val bankId: String,

    @ColumnInfo(name = "cacheBackMonth")
    val cacheBackMonth: Int,

    @ColumnInfo(name = "cacheBackYear")
    val cacheBackYear: Int,
)
