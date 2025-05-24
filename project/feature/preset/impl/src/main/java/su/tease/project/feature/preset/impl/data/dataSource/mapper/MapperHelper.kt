package su.tease.project.feature.preset.impl.data.dataSource.mapper

import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.impl.data.dao.PresetDao
import su.tease.project.feature.preset.impl.data.dao.entity.BankPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.mapper.toDomain

class MapperHelper(
    private val presetDao: PresetDao,
) {
    suspend fun BankPresetEntity.toDomain() =
        toDomain(getIcon = { getBankIconPreset(it) })

    private suspend fun getBankIconPreset(iconPresetId: String) =
        presetDao.bankIconPreset(iconPresetId).toDomain()

    suspend fun CashBackPresetEntity.toDomain() =
        toDomain(
            getIconPreset = { getCashBackIconPreset(it) },
            getBankPreset = { getBankPreset(it) },
            getCashBackCodePresets = { getCashBackCodePresets(it) }
        )

    private suspend fun getCashBackIconPreset(iconPresetId: String) = presetDao
        .cashBackIconPreset(iconPresetId)
        .toDomain()

    private suspend fun getBankPreset(bankPresetId: String) = presetDao
        .bankPreset(bankPresetId)
        .toDomain()

    private suspend fun getCashBackCodePresets(cashBackPresetId: String) = presetDao
        .mccCodeRelations(cashBackPresetId)
        .mapPersistent { getMccCode(it.mccCodePresetId) }

    private suspend fun getMccCode(mccCodePresetId: String) = presetDao
        .mccCodePreset(mccCodePresetId)
        .toDomain()
}
