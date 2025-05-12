package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackEntity")
data class CacheBackEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "cacheBackPresetId")
    val cacheBackPresetId: String,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "cacheBackMonth")
    val cacheBackMonth: Int,

    @ColumnInfo(name = "cacheBackYear")
    val cacheBackYear: Int,

    @ColumnInfo(name = "bankAccountId")
    val bankAccountId: String,
)
