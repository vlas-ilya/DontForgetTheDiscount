package su.tease.project.core.utils.ext

import kotlin.math.pow

fun Int.toPercent(fractionalSize: Int): String {
    val fractionalValue = 10.toDouble().pow(fractionalSize).toInt()
    val integer = this / fractionalValue
    val fractional = this % fractionalValue

    return if (fractional == 0) {
        "$integer"
    } else {
        val formated = fractional
            .toString()
            .padStart(2, '0')
            .removeSuffix("0")
        "$integer.$formated"
    }
}
