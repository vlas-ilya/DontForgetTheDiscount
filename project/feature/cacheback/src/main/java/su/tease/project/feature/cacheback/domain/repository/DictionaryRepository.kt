package su.tease.project.feature.cacheback.domain.repository

import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

interface DictionaryRepository {

    suspend fun banks(): List<BankPreset>

    suspend fun cacheBacks(): List<CacheBackPreset>

    suspend fun cacheBacksIcons(): List<CacheBackIcon>
}
