package su.tease.project.feature.cashback.data.repository.mapper

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import su.tease.project.feature.cashback.data.dao.entity.CashBackDateEntity
import su.tease.project.feature.cashback.data.dao.entity.CashBackEntity
import su.tease.project.feature.cashback.data.dependencies.PresetIntegrationInteractor
import su.tease.project.feature.cashback.domain.entity.CashBack
import su.tease.project.feature.cashback.domain.entity.CashBackDate
import su.tease.project.feature.cashback.domain.entity.preset.CashBackPreset

suspend fun List<CashBackEntity>.toDomain(
    presetIntegrationInteractor: PresetIntegrationInteractor,
): PersistentList<CashBack> {
    val cashBackPresetIds = map { it.presetId }
    val cashBackPresets = presetIntegrationInteractor.list(cashBackPresetIds).associateBy { it.id }
    return mapNotNull {
        val cashBackPreset = cashBackPresets[it.presetId] ?: return@mapNotNull null
        it.toDomain(cashBackPreset)
    }.toPersistentList()
}

fun CashBackEntity.toDomain(preset: CashBackPreset): CashBack = CashBack(
    id = id,
    preset = preset,
    size = size,
    date = CashBackDate(
        month = month,
        year = year
    ),
    ownerId = ownerId,
)

fun CashBackDateEntity.toDomain() = CashBackDate(
    month = month,
    year = year,
)

fun CashBack.toEntity() = CashBackEntity(
    id = id,
    presetId = preset.id,
    size = size,
    month = date.month,
    year = date.year,
    ownerId = ownerId,
)
