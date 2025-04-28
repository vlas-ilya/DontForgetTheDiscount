package su.tease.project.core.utils.ext

fun String.toIntSafe(): Int {
    val long = try {
        toLong()
    } catch (t: NumberFormatException) {
        return 0
    }

    val int = try {
        toInt()
    } catch (t: NumberFormatException) {
        if (long > 0) Int.MAX_VALUE else Int.MIN_VALUE
    }

    return int
}
