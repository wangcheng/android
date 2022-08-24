package io.github.wangcheng.weibolink

class WeiboLinkUtils {
    companion object {
        const val TAG = "WeiboLinkUtils"

        private const val WEIBO_QINGXIANG_PATTERN =
            "https?://share\\.api\\.weibo\\.cn/share/(\\d+),(\\d+).html.*"

        private const val WEIBO_INTL_WITH_ID_PATTERN =
            "https?://weibointl\\.api\\.weibo\\.cn/share/\\d+.html\\?weibo_id=(\\d+)"

        private const val WEIBO_CN_PATTERN =
            "https?://m\\.weibo\\.cn/.+/(\\d+)"

        fun createWeiboComUrl(userId: String, mId: String): String {
            return "https://weibo.com/${userId}/${Base62.mid2url(mId)}"
        }

        fun createWeiboCnUrl(mId: String): String {
            return "https://m.weibo.cn/status/${mId}"
        }

        fun getWeiboIdFromWeiboQingxiang(url: String): String? {
            val reg = Regex(WEIBO_QINGXIANG_PATTERN)
            val result = reg.matchEntire(url)
            return result?.groupValues?.let {
                it[2]
            }
        }

        fun getWeiboIdFromWeiboCn(url: String): String? {
            val reg = Regex(WEIBO_CN_PATTERN)
            val result = reg.matchEntire(url)
            return result?.groupValues?.let {
                it[1]
            }
        }

        fun getWeiboIdFromWeiboIntlWithIdQuery(url: String): String? {
            val reg = Regex(WEIBO_INTL_WITH_ID_PATTERN)
            val result = reg.matchEntire(url)
            return result?.groupValues?.let {
                it[1]
            }
        }

        fun getWeiboIdFromUrl(url: String): String? {
            var weiboId: String?
            weiboId = getWeiboIdFromWeiboCn(url)
            if (weiboId == null) {
                weiboId = getWeiboIdFromWeiboQingxiang(url)
            }
            if (weiboId == null) {
                weiboId = getWeiboIdFromWeiboIntlWithIdQuery(url)
            }
            return weiboId
        }
    }
}
