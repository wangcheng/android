package io.github.wangcheng.weibolink

import org.junit.Assert
import org.junit.Test

class WeiboLinkUtilsTest {
    @Test
    fun testConvertWeiboQingxiang() {
        val testUrl = "https://share.api.weibo.cn/share/330291556,4805380419357654.html"
        Assert.assertEquals(
            WeiboLinkUtils.convertWeiboQingxiang(testUrl),
            "https://weibo.com/330291556/M2fY5DglU"
        )
    }

    @Test
    fun testConvertWeiboQingxiangWithQuery() {
        val testUrl =
            "https://share.api.weibo.cn/share/330291556,4805380419357654.html?weibo_id=4805380419357654"
        Assert.assertEquals(
            WeiboLinkUtils.convertWeiboQingxiang(testUrl),
            "https://weibo.com/330291556/M2fY5DglU"
        )
    }

    @Test
    fun testConvertWeiboQingxiangNull() {
        val testUrl = "https://share.api.weibo.cn/share/330291556.html"
        Assert.assertNull(WeiboLinkUtils.convertWeiboQingxiang(testUrl))
    }
}
