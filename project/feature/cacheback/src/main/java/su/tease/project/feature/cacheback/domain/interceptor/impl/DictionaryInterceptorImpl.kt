package su.tease.project.feature.cacheback.domain.interceptor.impl

import kotlinx.collections.immutable.PersistentList
import su.tease.project.core.utils.utils.withDefault
import su.tease.project.feature.cacheback.domain.entity.preset.BankPreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackCodePreset
import su.tease.project.feature.cacheback.domain.entity.preset.CacheBackPreset
import su.tease.project.feature.cacheback.domain.entity.preset.IconPreset
import su.tease.project.feature.cacheback.domain.interceptor.DictionaryInterceptor
import su.tease.project.feature.cacheback.domain.repository.DictionaryRepository

class DictionaryInterceptorImpl(
    private val repository: DictionaryRepository
) : DictionaryInterceptor {

    override suspend fun banks(): PersistentList<BankPreset> = withDefault {
        repository.banks()
    }

    override suspend fun cacheBacks(): PersistentList<CacheBackPreset> = withDefault {
        repository.cacheBacks()
    }

    override suspend fun cacheBacksIcons(): PersistentList<IconPreset> = withDefault {
        repository.cacheBacksIcons()
    }

    override suspend fun bankIcons() = withDefault {
        repository.bankIcons()
    }

    override suspend fun cacheBacksCodes(): PersistentList<CacheBackCodePreset> = withDefault {
        repository.cacheBacksCodes()
    }

    override suspend fun add(bank: BankPreset) = withDefault {
        repository.add(bank)
    }

    override suspend fun add(cacheBack: CacheBackPreset) = withDefault {
        repository.add(cacheBack)
    }

    override suspend fun save(code: CacheBackCodePreset) = withDefault {
        repository.save(code)
    }
}
