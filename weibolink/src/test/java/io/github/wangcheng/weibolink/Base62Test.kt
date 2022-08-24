package io.github.wangcheng.weibolink

import org.junit.Assert
import org.junit.Test

class Base62Test {
    @Test
    fun testMid2url() {
        Assert.assertEquals("wr4mOFqpbO", Base62.mid2url("201110410216293360"))
    }

    @Test
    fun testUrl2mid() {
        Assert.assertEquals("201110410216293360", Base62.url2mid("wr4mOFqpbO"))
    }
}
