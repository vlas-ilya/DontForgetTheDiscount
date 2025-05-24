package su.tease.project.feature.preset.impl.data.dataSource.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.api.domain.entity.BankPreset
import su.tease.project.feature.preset.api.domain.entity.CashBackPreset
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.impl.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.impl.data.dataSource.dto.CashBackPresetDto

fun CashBackPresetDto.toDomain(bankPreset: BankPreset) = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    bankPreset = bankPreset,
    mccCodes = mccCodes.toDomain(),
)

fun CashBackPresetDto.toEntity(bankPresetId: String) = CashBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    bankPresetId = bankPresetId,
)

fun CashBackPresetDto.toMccCodeRelations(): PersistentList<CashBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CashBackPresetToMccCodePresetEntity(id, it.id) }

fun CashBackPresetDto.toEntityWithRelations(bankPresetId: String) = Pair(
    toEntity(bankPresetId),
    toMccCodeRelations(),
)
