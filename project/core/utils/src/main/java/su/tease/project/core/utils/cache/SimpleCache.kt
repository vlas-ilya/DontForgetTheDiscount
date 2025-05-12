package su.tease.project.core.utils.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SimpleCache {
    private val cache = mutableMapOf<String, Any>()
    private val locks = mutableMapOf<String, Mutex>()
    private val locksMutex = Mutex()

    @Suppress("UNCHECKED_CAST")
    suspend fun <R> getOrPut(
        key: String,
        defaultValue: suspend () -> R
    ): R = (
        cache[key] ?: getKeyMutex(key)
            .withLock { cache.getOrPut(key) { defaultValue() as Any } }
            .also { removeKeyMutex(key) }
        ) as R

    suspend fun clear() = locksMutex.withLock {
        cache.clear()
        locks.clear()
    }

    private suspend fun getKeyMutex(key: String) =
        locksMutex.withLock { locks.getOrPut(key) { Mutex() } }

    private suspend fun removeKeyMutex(key: String) =
        locksMutex.withLock { locks.remove(key) }
}
