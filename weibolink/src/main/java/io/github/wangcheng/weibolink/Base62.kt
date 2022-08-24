package io.github.wangcheng.weibolink

import kotlin.math.floor
import kotlin.math.pow

class Base62 {
    companion object {
        private const val DICT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        // 62进制值转换为10进制
        private fun decode(inputStr: String): String {
            var i10 = 0
            for (i in inputStr.indices) {
                val n = inputStr.length - i - 1
                val s = inputStr[i]
                i10 += DICT.indexOf(s) * 62F.pow(n).toInt()
            }
            return i10.toString()
        }

        // 10进制值转换为62进制
        private fun encode(str10: String): String {
            var int10 = str10.toInt(10)
            var s62 = ""
            var r: Int
            while (int10 != 0 && s62.length < 100) {
                r = int10 % 62
                s62 = DICT[r] + s62
                int10 = floor(int10 / 62F).toInt()
            }
            return s62
        }

        private fun chunkString(str: String, size: Int): List<String> {
            return str.reversed().chunked(size).map { it.reversed() }.reversed()
        }

        fun url2mid(str: String): String {
            return chunkString(str, 4).joinToString(
                separator = "",
                transform = { decode(it).padStart(7, '0') }
            ).trimStart('0')
        }

        fun mid2url(str: String): String {
            return chunkString(str, 7).joinToString(
                separator = "",
                transform = {
                    encode(it).padStart(4, '0')
                }
            ).trimStart('0')
        }
    }
}
