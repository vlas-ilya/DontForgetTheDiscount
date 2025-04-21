package su.tease.project.core.utils.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import su.tease.project.core.utils.ext.unit

class SimpleCache {
    private val cache = mutableMapOf<Any, Any>()
    private val locks = mutableMapOf<Any, Mutex>()
    private val locksMutex = Mutex()

    @Suppress("UNCHECKED_CAST")
    suspend fun <R> getOrPut(
        key: Any,
        defaultValue: suspend () -> R
    ): R = (
        cache[key] ?: getKeyMutex(key)
            .withLock { cache.getOrPut(key) { defaultValue() as Any } }
            .also { removeKeyMutex(key) }
        ) as R

    suspend fun clear(key: Any) = getKeyMutex(key).withLock {
        cache.remove(key)
        removeKeyMutex(key)
    }.unit()

    private suspend fun getKeyMutex(key: Any) =
        locksMutex.withLock { locks.getOrPut(key) { Mutex() } }

    private suspend fun removeKeyMutex(key: Any) =
        locksMutex.withLock { locks.remove(key) }
}
