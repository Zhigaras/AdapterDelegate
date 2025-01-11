package com.zhigaras.adapter_delegate

import kotlin.random.Random

object RandomStringGenerator {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generate(length: Int): String = (1..length)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}
