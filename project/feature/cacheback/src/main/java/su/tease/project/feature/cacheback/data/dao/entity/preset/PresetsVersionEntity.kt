package su.tease.project.feature.cacheback.data.dao.entity.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PresetsVersionEntity")
data class PresetsVersionEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "banks")
    val banks: Int,

    @ColumnInfo(name = "bankIcons")
    val bankIcons: Int,

    @ColumnInfo(name = "cacheBackIcons")
    val cacheBackIcons: Int,

    @ColumnInfo(name = "mccCodes")
    val mccCodes: Int,
)
