package su.tease.core.clean.domain.repository

import kotlinx.collections.immutable.PersistentList
import su.tease.core.clean.domain.entity.EntityId

interface CrudRepository<T> {
    suspend fun save(bank: T)
    suspend fun find(id: EntityId<T>): T?
    suspend fun get(id: EntityId<T>): T = find(id) ?: throw RepositoryException()
    suspend fun list(): PersistentList<T>
    suspend fun delete(id: EntityId<T>): Boolean
}
