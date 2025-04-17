package su.tease.project.core.utils.ext

fun <Key, Value> MutableMap<Key, Value>.applyAndGet(
    key: Key,
    default: Value,
    block: (Value) -> Value
): Value = block(getOrPut(key) { default }).also { put(key, it) }
