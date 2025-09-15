package su.tease.project.feature.preset.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MccCodePresetEntity")
data class MccCodePresetEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "info")
    val info: String,
)
