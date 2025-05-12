package su.tease.project.feature.cacheback.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BankAccountEntity")
data class BankAccountEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "bankPresetId")
    val bankPresetId: String,

    @ColumnInfo(name = "customName")
    val customName: String,
)
