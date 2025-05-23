package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.impl.data.dao.PresetDao
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CacheBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.mapper.toDomain

class MapperHelper(
    private val presetDao: PresetDao,
) {
    suspend fun BankPresetEntity.toDomain() =
        toDomain(getIcon = { getBankIconPreset(it) })

    private suspend fun getBankIconPreset(iconPresetId: String) =
        presetDao.bankIconPreset(iconPresetId).toDomain()

    suspend fun CacheBackPresetEntity.toDomain() =
        toDomain(
            getIconPreset = { getCacheBackIconPreset(it) },
            getBankPreset = { getBankPreset(it) },
            getCacheBackCodePresets = { getCacheBackCodePresets(it) }
        )

    private suspend fun getCacheBackIconPreset(iconPresetId: String) = presetDao
        .cacheBackIconPreset(iconPresetId)
        .toDomain()

    private suspend fun getBankPreset(bankPresetId: String) = presetDao
        .bankPreset(bankPresetId)
        .toDomain()

    private suspend fun getCacheBackCodePresets(cacheBackPresetId: String) = presetDao
        .mccCodeRelations(cacheBackPresetId)
        .mapPersistent { getMccCode(it.mccCodePresetId) }

    private suspend fun getMccCode(mccCodePresetId: String) = presetDao
        .mccCodePreset(mccCodePresetId)
        .toDomain()
}
