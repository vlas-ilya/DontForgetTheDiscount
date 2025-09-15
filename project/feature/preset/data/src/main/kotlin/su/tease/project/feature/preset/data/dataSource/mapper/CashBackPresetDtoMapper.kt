package su.tease.project.feature.preset.data.dataSource.mapper

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.ext.mapPersistent
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetEntity
import su.tease.project.feature.preset.data.dao.entity.CashBackPresetToMccCodePresetEntity
import su.tease.project.feature.preset.data.dataSource.dto.CashBackPresetDto
import su.tease.project.feature.preset.domain.entity.BankPreset
import su.tease.project.feature.preset.domain.entity.CashBackPreset

fun CashBackPresetDto.toDomain(cashBackOwnerPreset: BankPreset) = CashBackPreset(
    id = id,
    name = name,
    info = info,
    iconPreset = iconPreset.toDomain(),
    cashBackOwnerPreset = cashBackOwnerPreset,
    mccCodes = mccCodes.toDomain(),
)

fun CashBackPresetDto.toEntity(bankPresetId: String) = CashBackPresetEntity(
    id = id,
    name = name,
    info = info,
    iconPresetId = iconPreset.id,
    cashBackOwnerPresetId = bankPresetId,
)

fun CashBackPresetDto.toMccCodeRelations(): PersistentList<CashBackPresetToMccCodePresetEntity> =
    mccCodes.mapPersistent { CashBackPresetToMccCodePresetEntity(id, it.id) }

fun CashBackPresetDto.toEntityWithRelations(bankPresetId: String) = Pair(
    toEntity(bankPresetId),
    toMccCodeRelations(),
)
