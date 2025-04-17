package su.tease.core.clean.domain.inteceptor

import su.tease.core.clean.domain.entity.EntityId

interface CrudInterceptor<T> {
    suspend fun save(bank: T): T
    suspend fun get(id: EntityId<T>): T
    suspend fun list(): T
    suspend fun delete(id: EntityId<T>): T
}
