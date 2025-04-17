package su.tease.project.feature.cacheback.domain.interceptor.impl

import su.tease.core.clean.domain.entity.EntityId
import su.tease.project.feature.cacheback.domain.entity.CacheBackBank
import su.tease.project.feature.cacheback.domain.interceptor.CacheBackBankInterceptor

class CacheBackBankInterceptorImpl : CacheBackBankInterceptor {

    override suspend fun save(bank: CacheBackBank): CacheBackBank {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: EntityId<CacheBackBank>): CacheBackBank {
        TODO("Not yet implemented")
    }

    override suspend fun list(): CacheBackBank {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: EntityId<CacheBackBank>): CacheBackBank {
        TODO("Not yet implemented")
    }
}
