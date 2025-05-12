package su.tease.core.clean.domain.repository

import kotlinx.collections.immutable.PersistentList

interface CrudRepository<T> {
    suspend fun save(value: T)
    suspend fun find(id: String): T?
    suspend fun get(id: String): T = find(id) ?: throw RepositoryException()
    suspend fun list(): PersistentList<T>
    suspend fun delete(id: String): Boolean
}
