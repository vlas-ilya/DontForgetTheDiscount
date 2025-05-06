package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "CacheBackEntity")
class CacheBackDateEntity(

    @ColumnInfo(name = "cacheBackMonth")
    val cacheBackMonth: Int,

    @ColumnInfo(name = "cacheBackYear")
    val cacheBackYear: Int,
)
