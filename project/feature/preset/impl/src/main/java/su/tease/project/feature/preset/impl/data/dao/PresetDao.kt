package su.tease.project.feature.preset.impl.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import su.tease.project.feature.preset.impl.data.dao.entity.BankIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackIconPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetToMccCodePresetEntity
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
    suspend fun save(entity: CacheBackPresetEntity)

    @Query("SELECT * FROM CacheBackPresetEntity WHERE bankPresetId = :bankPresetId")
    suspend fun cacheBackPresets(bankPresetId: String): List<CacheBackPresetEntity>

    @Query("SELECT * FROM CacheBackPresetEntity WHERE id = :id")
    suspend fun cacheBackPreset(id: String): CacheBackPresetEntity

    @Upsert
    suspend fun save(entity: CacheBackIconPresetEntity)

    @Query("SELECT * FROM CacheBackIconPresetEntity")
    suspend fun cacheBackIconPresets(): List<CacheBackIconPresetEntity>

    @Query("SELECT * FROM CacheBackIconPresetEntity WHERE id = :id")
    suspend fun cacheBackIconPreset(id: String): CacheBackIconPresetEntity

    @Upsert
    suspend fun save(entity: CacheBackPresetToMccCodePresetEntity)

    @Query("DELETE FROM CacheBackPresetToMccCodePresetEntity WHERE cacheBackPresetId = :cacheBackPresetId")
    suspend fun removeMccCodeRelations(cacheBackPresetId: String)

    @Query("SELECT * FROM CacheBackPresetToMccCodePresetEntity WHERE cacheBackPresetId = :cacheBackPresetId")
    suspend fun mccCodeRelations(cacheBackPresetId: String): List<CacheBackPresetToMccCodePresetEntity>

    @Upsert
    suspend fun save(entity: MccCodePresetEntity)

    @Query("SELECT * FROM MccCodePresetEntity")
    suspend fun mccCodePresets(): List<MccCodePresetEntity>

    @Query("SELECT * FROM MccCodePresetEntity WHERE id = :id")
    suspend fun mccCodePreset(id: String): MccCodePresetEntity
}
