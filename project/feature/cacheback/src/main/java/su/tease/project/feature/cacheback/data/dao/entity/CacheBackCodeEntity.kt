package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheBackCodeEntity")
data class CacheBackCodeEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "cacheBackId")
    val cacheBackId: String
)
