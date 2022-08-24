package io.github.wangcheng.weibolink

import org.junit.Assert
import org.junit.Test

class WeiboLinkUtilsTest {
    @Test
    fun testGetWeiboIdFromWeiboCn() {
        val testUrl = "https://m.weibo.cn/status/4795462463524930"
        Assert.assertEquals("4795462463524930", WeiboLinkUtils.getWeiboIdFromWeiboCn(testUrl))
    }

    @Test
    fun testGetWeiboIdFromWeiboCnWithUserId() {
        val testUrl = "https://m.weibo.cn/6622612758/4795462463524930"
        Assert.assertEquals("4795462463524930", WeiboLinkUtils.getWeiboIdFromWeiboCn(testUrl))
    }

    @Test
    fun testGetWeiboIdFromWeiboCnNull() {
        val testUrl =
            "https://share.api.weibo.cn/share/330291556,4805380419357654.html?weibo_id=4805380419357654"
        Assert.assertNull(WeiboLinkUtils.getWeiboIdFromWeiboCn(testUrl))
    }
}
