package su.tease.project.feature.cacheback.domain.interceptor.impl

import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackIcon
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository

class DictionaryInterceptorImpl(
    private val repository: DictionaryRepository
) : DictionaryInterceptor {

    override suspend fun banks(): List<BankPreset> = withDefault {
        repository.banks()
    }

    override suspend fun cacheBacks(): List<CacheBackPreset> = withDefault {
        repository.cacheBacks()
    }

    override suspend fun cacheBacksIcons(): List<CacheBackIcon> = withDefault {
        repository.cacheBacksIcons()
    }
}
