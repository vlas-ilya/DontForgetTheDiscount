package su.tease.project.feature.cacheback.domain.interceptor

import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset

interface DictionaryInterceptor {

    suspend fun banks(): List<BankPreset>

    suspend fun cacheBacks(): List<CacheBackPreset>

    suspend fun cacheBacksIcons(): List<CacheBackIcon>
}
