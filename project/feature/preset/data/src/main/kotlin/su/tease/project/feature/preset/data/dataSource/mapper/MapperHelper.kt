package su.tease.project.feature.preset.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.data.dao.PresetDao
import su.tease.project.feature.preset.data.dao.entity.CashBackOwnerPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.data.dao.mapper.toDomain

class MapperHelper(
    private val presetDao: PresetDao,
) {
    suspend fun CashBackOwnerPresetEntity.toDomain() =
        toDomain(getIcon = { getCashBackOwnerIconPreset(it) })

    private suspend fun getCashBackOwnerIconPreset(iconPresetId: String) =
        presetDao.cashBackOwnerIconPreset(iconPresetId).toDomain()

    suspend fun CashBackPresetEntity.toDomain() =
        toDomain(
            getIconPreset = { getCashBackIconPreset(it) },
            getCashBackOwnerPreset = { getCashBackOwnerPreset(it) },
            getCashBackCodePresets = { getCashBackCodePresets(it) }
        )

    private suspend fun getCashBackIconPreset(iconPresetId: String) = presetDao
        .cashBackIconPreset(iconPresetId)
        .toDomain()

    private suspend fun getCashBackOwnerPreset(cashBackOwnerPresetId: String) = presetDao
        .cashBackOwnerPreset(cashBackOwnerPresetId)
        .toDomain()

    private suspend fun getCashBackCodePresets(cashBackPresetId: String) = presetDao
        .mccCodeRelations(cashBackPresetId)
        .mapPersistent { getMccCode(it.mccCodePresetId) }

    private suspend fun getMccCode(mccCodePresetId: String) = presetDao
        .mccCodePreset(mccCodePresetId)
        .toDomain()
}
