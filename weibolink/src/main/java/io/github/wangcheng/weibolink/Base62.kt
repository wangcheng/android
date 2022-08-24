package io.github.wangcheng.weibolink

import kotlin.math.pow

class Base62 {
    companion object {
        private const val DICT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        // 62进制值转换为10进制
        private fun decode(inputStr: String): String {
            var int10 = 0
            for (i in inputStr.indices) {
                val powerNumber = inputStr.length - i - 1
                val char = inputStr[i]
                int10 += DICT.indexOf(char) * 62F.pow(powerNumber).toInt()
            }
            return int10.toString()
        }

        // 10进制值转换为62进制
        private fun encode(str10: String): String {
            var int10 = str10.toInt()
            var str62 = ""
            while (int10 != 0 && str62.length < 100) {
                str62 = DICT[int10 % 62] + str62
                int10 = int10.floorDiv(62)
            }
            return str62
        }

        private fun chunkString(str: String, size: Int): List<String> =
            str.reversed()
                .chunked(size)
                .map { it.reversed() }
                .reversed()

        fun url2mid(str: String): String = chunkString(str, 4).joinToString(
            separator = "",
            transform = { decode(it).padStart(7, '0') },
        ).trimStart('0')

        fun mid2url(str: String): String = chunkString(str, 7).joinToString(
            separator = "",
            transform = {
                encode(it).padStart(4, '0')
            },
        ).trimStart('0')
    }
}
