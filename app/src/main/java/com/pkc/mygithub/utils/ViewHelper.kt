package com.pkc.mygithub.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun String.toShortNumber(): String {
    val doubleNumber = this.toDouble()
    val formatter = DecimalFormat("0.#")
    val isNumberLessThanThousand = this.toInt() < 1000.0
    formatter.roundingMode = RoundingMode.DOWN

    return if (isNumberLessThanThousand) {
        formatter.format(doubleNumber)
    } else {
        val exp = (ln(doubleNumber) / ln(1000.0)).toInt()
        formatter.format(this.toInt() / 1000.0.pow(exp.toDouble())) + "KMBTPE"[exp - 1]
    }
}
