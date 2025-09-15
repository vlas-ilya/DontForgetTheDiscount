package su.tease.project.feature.preset.data.dao.entity

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

    @ColumnInfo(name = "shops")
    val shops: Int,

    @ColumnInfo(name = "shopIcons")
    val shopIcons: Int,

    @ColumnInfo(name = "cashBackIcons")
    val cashBackIcons: Int,

    @ColumnInfo(name = "mccCodes")
    val mccCodes: Int,
)
