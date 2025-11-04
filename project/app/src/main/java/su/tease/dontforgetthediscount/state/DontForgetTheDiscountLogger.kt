package su.tease.dontforgetthediscount.state

import su.tease.project.core.mvi.api.action.PlainAction
import su.tease.project.core.mvi.api.state.State
import su.tease.project.core.mvi.impl.logger.StoreLogger
import timber.log.Timber
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

val dontForgetTheDiscountLogger = object : StoreLogger {
    override fun log(prevState: State, action: PlainAction, newState: State) {
        Timber.d("Redux action: ${action.deepToStringPretty()}")
    }
}


fun Any?.deepToStringPretty(
    path: MutableSet<Int> = mutableSetOf(),
    indent: String = ""
): String {
    if (this == null) return "null"

    val clazz = this::class

    // Примитивы и простые типы
    if (clazz.javaPrimitiveType != null ||
        this is String || this is Number || this is Boolean ||
        this is Enum<*> || this is Char
    ) return this.toString()

    val identity = System.identityHashCode(this)
    if (!path.add(identity)) return "${clazz.fullNestedName()}(↻)"

    val name = clazz.fullNestedName()

    // Коллекции
    if (this is Iterable<*>) {
        val items = this.joinToString(",\n$indent\t") { it.deepToStringPretty(path, "$indent\t") }
        path.remove(identity)
        return "$name([\n$indent\t$items\n$indent])"
    }

    // Map
    if (this is Map<*, *>) {
        val entries = this.entries.joinToString(",\n$indent\t") { (k, v) ->
            "${k.deepToStringPretty(path, "$indent\t")}=${v.deepToStringPretty(path, "$indent\t")}"
        }
        path.remove(identity)
        return "$name({\n$indent\t$entries\n$indent})"
    }

    // Свойства объекта
    val props = clazz.memberProperties
        .filterNot { it.name in setOf("INSTANCE", "Companion", "\$stable", "compare") }
        .joinToString(",\n$indent\t") { prop ->
            val value = try {
                prop.getter.call(this).deepToStringPretty(path, "$indent\t")
            } catch (_: Exception) {
                "?"
            }
            "${prop.name}=$value"
        }

    path.remove(identity)
    return if (props.isNotEmpty()) "$name(\n$indent\t$props\n$indent)" else name
}

// строим полное имя без пакета, с вложенностью
private fun KClass<*>.fullNestedName(): String {
    val parts = mutableListOf<String>()
    var current: Class<*>? = this.java
    while (current != null) {
        parts += current.simpleName
        current = current.enclosingClass
    }
    return parts.reversed().joinToString(".")
}