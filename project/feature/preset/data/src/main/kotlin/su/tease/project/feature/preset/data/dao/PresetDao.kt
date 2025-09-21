package su.tease.project.feature.preset.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.preset.data.dao.entity.CashBackIconPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerIconPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.data.dao.entity.MccCodePresetEntity
import su.tease.project.feature.preset.data.dao.entity.PresetsVersionEntity
import su.tease.project.feature.preset.data.dao.mapper.BANK_ICON_PRESET_TYPE
import su.tease.project.feature.preset.data.dao.mapper.BANK_PRESET_TYPE
import su.tease.project.feature.preset.data.dao.mapper.SHOP_ICON_PRESET_TYPE
import su.tease.project.feature.preset.data.dao.mapper.SHOP_PRESET_TYPE

@Dao
@Suppress("TooManyFunctions")
interface PresetDao {

    @Query("SELECT * FROM PresetsVersionEntity")
    suspend fun version(): PresetsVersionEntity?

    @Upsert
    suspend fun save(entity: PresetsVersionEntity)

    @Upsert
    suspend fun save(entity: CashBackOwnerPresetEntity)

    @Query("SELECT * FROM CashBackOwnerPresetEntity where type = '$BANK_PRESET_TYPE' ORDER BY name")
    suspend fun bankPresets(): List<CashBackOwnerPresetEntity>

    @Query("SELECT * FROM CashBackOwnerPresetEntity where type = '$BANK_PRESET_TYPE' AND id in (:bankPresetIds) ORDER BY name")
    suspend fun bankPresets(bankPresetIds: List<String>): List<CashBackOwnerPresetEntity>

    @Query("SELECT * FROM CashBackOwnerPresetEntity WHERE id = :id AND type = '$BANK_PRESET_TYPE'")
    suspend fun bankPreset(id: String): CashBackOwnerPresetEntity

    @Query("SELECT * FROM CashBackOwnerPresetEntity where type = '$SHOP_PRESET_TYPE' ORDER BY name")
    suspend fun shopPresets(): List<CashBackOwnerPresetEntity>

    @Query("SELECT * FROM CashBackOwnerPresetEntity where type = '$SHOP_PRESET_TYPE' AND id in (:shopPresetIds) ORDER BY name")
    suspend fun shopPresets(shopPresetIds: List<String>): List<CashBackOwnerPresetEntity>

    @Query("SELECT * FROM CashBackOwnerPresetEntity WHERE id = :id AND type = '$SHOP_PRESET_TYPE'")
    suspend fun shopPreset(id: String): CashBackOwnerPresetEntity

    @Query("SELECT * FROM CashBackOwnerPresetEntity ORDER BY name")
    suspend fun cashBackOwnerPresets(): List<CashBackOwnerPresetEntity>

    @Query("SELECT * FROM CashBackOwnerPresetEntity WHERE id = :id")
    suspend fun cashBackOwnerPreset(id: String): CashBackOwnerPresetEntity

    @Upsert
    suspend fun save(entity: CashBackOwnerIconPresetEntity)

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity WHERE type = '$BANK_ICON_PRESET_TYPE' ORDER BY iconUrl")
    suspend fun bankIconPresets(): List<CashBackOwnerIconPresetEntity>

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity WHERE id = :id AND type = '$BANK_ICON_PRESET_TYPE'")
    suspend fun bankIconPreset(id: String): CashBackOwnerIconPresetEntity

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity WHERE type = '$SHOP_ICON_PRESET_TYPE' ORDER BY iconUrl")
    suspend fun shopIconPresets(): List<CashBackOwnerIconPresetEntity>

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity WHERE id = :id AND type = '$SHOP_ICON_PRESET_TYPE'")
    suspend fun shopIconPreset(id: String): CashBackOwnerIconPresetEntity

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity ORDER BY iconUrl")
    suspend fun cashBackOwnerIconPresets(): List<CashBackOwnerIconPresetEntity>

    @Query("SELECT * FROM CashBackOwnerIconPresetEntity WHERE id = :id")
    suspend fun cashBackOwnerIconPreset(id: String): CashBackOwnerIconPresetEntity

    @Upsert
    suspend fun save(entity: CashBackPresetEntity)

    @Query("SELECT * FROM CashBackPresetEntity WHERE cashBackOwnerPresetId = :cashBackOwnerPresetId ORDER BY name")
    suspend fun cashBackPresets(cashBackOwnerPresetId: String): List<CashBackPresetEntity>

    @Query("SELECT * FROM CashBackPresetEntity WHERE id in (:ids) ORDER BY name")
    suspend fun cashBackPresets(ids: List<String>): List<CashBackPresetEntity>

    @Query("SELECT * FROM CashBackPresetEntity WHERE id = :id")
    suspend fun cashBackPreset(id: String): CashBackPresetEntity

    @Upsert
    suspend fun save(entity: CashBackIconPresetEntity)

    @Query("SELECT * FROM CashBackIconPresetEntity ORDER BY iconUrl")
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
