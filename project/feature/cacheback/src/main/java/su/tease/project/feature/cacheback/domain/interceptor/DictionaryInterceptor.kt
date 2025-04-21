package su.tease.project.feature.cacheback.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

interface DictionaryInterceptor {

    suspend fun banks(): PersistentList<BankPreset>

    suspend fun cacheBacks(): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIcons(): PersistentList<IconPreset>

    suspend fun add(bank: BankPreset)

    suspend fun add(cacheBank: CacheBackPreset)
}
