package su.tease.project.feature.shop.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShopEntity")
data class ShopEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "presetId")
    val presetId: String,

    @ColumnInfo(name = "customName")
    val customName: String,
)
