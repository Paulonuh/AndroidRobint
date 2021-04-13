package com.paulo.teixeira.robint.extension

import java.text.Normalizer

fun CharSequence.unaccent(): String {
    val regexUnaccent = "\\p{InCombiningDiacriticalMarks}+".toRegex()

    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return regexUnaccent.replace(temp, "")
}