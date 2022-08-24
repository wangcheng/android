package io.github.wangcheng.weibolink

class WeiboLinkUtils {
    companion object {
        const val TAG = "WeiboLinkUtils"

        private const val WEIBO_QINGXIANG_PATTERN =
            "https?://share\\.api\\.weibo\\.cn/share/(\\d+),(\\d+).html.*"

        private fun createWeiboComUrl(userId: String, mId: String): String {
            return "https://weibo.com/${userId}/${Base62.mid2url(mId)}"
        }

        private fun createWeiboCnUrl(mId: String): String {
            return "https://m.weibo.cn/status/${mId}"
        }

        // 微博轻享版
        fun convertWeiboQingxiang(url: String): String? {
            val reg = Regex(WEIBO_QINGXIANG_PATTERN)
            val result = reg.matchEntire(url)
            println(TAG + result?.groupValues.toString())
            return result?.groupValues?.let {
                createWeiboComUrl(it[1], it[2])
            }
        }

        fun isWeiboQingxiang(url: String): Boolean {
            val reg = Regex(WEIBO_QINGXIANG_PATTERN)
            return reg.matches(url)
        }
    }
}
