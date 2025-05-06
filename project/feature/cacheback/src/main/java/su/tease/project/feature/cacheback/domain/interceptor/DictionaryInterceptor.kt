package su.tease.project.feature.cacheback.domain.interceptor

import kotlinx.collections.immutable.PersistentList
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackCodePreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset

interface DictionaryInterceptor {

    suspend fun banks(): PersistentList<BankPreset>

    suspend fun cacheBacks(): PersistentList<CacheBackPreset>

    suspend fun cacheBacksIcons(): PersistentList<IconPreset>

    suspend fun bankIcons(): PersistentList<IconPreset>

    suspend fun cacheBacksCodes(): PersistentList<CacheBackCodePreset>

    suspend fun add(bank: BankPreset)

    suspend fun add(cacheBack: CacheBackPreset)

    suspend fun save(code: CacheBackCodePreset)
}
