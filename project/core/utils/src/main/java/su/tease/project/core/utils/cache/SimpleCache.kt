package su.tease.project.core.utils.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import su.tease.project.core.utils.ext.unit

class SimpleCache {
    private val cache = mutableMapOf<String, Any>()
    private val locks = mutableMapOf<String, Mutex>()
    private val locksMutex = Mutex()

    @Suppress("UNCHECKED_CAST")
    suspend fun <R> getOrPut(
        key: String,
        defaultValue: suspend () -> R
    ): R = run {
        cache[key] ?: getKeyMutex(key)
            .withLock { cache.getOrPut(key) { defaultValue() as Any } }
            .also { removeKeyMutex(key) }
    } as R

    @Suppress("UNCHECKED_CAST")
    fun <R> get(
        key: String,
    ): R? = cache[key] as? R

    @Suppress("UNCHECKED_CAST")
    suspend fun put(
        key: String,
        value: Any
    ): Unit = getKeyMutex(key)
        .withLock { cache.put(key, value) }
        .also { removeKeyMutex(key) }
        .unit()

    suspend fun clear(key: String) = locksMutex.withLock {
        cache.remove(key)
        locks.remove(key)
    }.unit()

    private suspend fun getKeyMutex(key: String) =
        locksMutex.withLock { locks.getOrPut(key) { Mutex() } }

    private suspend fun removeKeyMutex(key: String) =
        locksMutex.withLock { locks.remove(key) }
}
