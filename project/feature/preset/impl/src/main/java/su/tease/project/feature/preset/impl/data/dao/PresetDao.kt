package su.tease.project.feature.preset.impl.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.PresetsVersionEntity

@Dao
@Suppress("TooManyFunctions")
interface PresetDao {

    @Query("SELECT * FROM PresetsVersionEntity")
    suspend fun version(): PresetsVersionEntity?

    @Upsert
    suspend fun save(entity: PresetsVersionEntity)

    @Upsert
    suspend fun save(entity: BankPresetEntity)

    @Query("SELECT * FROM BankPresetEntity")
    suspend fun bankPresets(): List<BankPresetEntity>

    @Query("SELECT * FROM BankPresetEntity WHERE id = :id")
    suspend fun bankPreset(id: String): BankPresetEntity

    @Upsert
    suspend fun save(entity: BankIconPresetEntity)

    @Query("SELECT * FROM BankIconPresetEntity")
    suspend fun bankIconPresets(): List<BankIconPresetEntity>

    @Query("SELECT * FROM BankIconPresetEntity WHERE id = :id")
    suspend fun bankIconPreset(id: String): BankIconPresetEntity

    @Upsert
    suspend fun save(entity: CashBackPresetEntity)

    @Query("SELECT * FROM CashBackPresetEntity WHERE bankPresetId = :bankPresetId")
    suspend fun cashBackPresets(bankPresetId: String): List<CashBackPresetEntity>

    @Query("SELECT * FROM CashBackPresetEntity WHERE id = :id")
    suspend fun cashBackPreset(id: String): CashBackPresetEntity

    @Upsert
    suspend fun save(entity: CashBackIconPresetEntity)

    @Query("SELECT * FROM CashBackIconPresetEntity")
    suspend fun cashBackIconPresets(): List<CashBackIconPresetEntity>

    @Query("SELECT * FROM CashBackIconPresetEntity WHERE id = :id")
    suspend fun cashBackIconPreset(id: String): CashBackIconPresetEntity

    @Upsert
    suspend fun save(entity: CashBackPresetToMccCodePresetEntity)

    @Query("DELETE FROM CashBackPresetToMccCodePresetEntity WHERE cashBackPresetId = :cashBackPresetId")
    suspend fun removeMccCodeRelations(cashBackPresetId: String)

    @Query("SELECT * FROM CashBackPresetToMccCodePresetEntity WHERE cashBackPresetId = :cashBackPresetId")
    suspend fun mccCodeRelations(cashBackPresetId: String): List<CashBackPresetToMccCodePresetEntity>

    @Upsert
    suspend fun save(entity: MccCodePresetEntity)

    @Query("SELECT * FROM MccCodePresetEntity")
    suspend fun mccCodePresets(): List<MccCodePresetEntity>

    @Query("SELECT * FROM MccCodePresetEntity WHERE id = :id")
    suspend fun mccCodePreset(id: String): MccCodePresetEntity
}
