package io.github.wangcheng.weibolink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Intent.ACTION_VIEW) {
            openWeiboDetailPageFromUrl(intent.data.toString())
        }
        finish()
    }

    private fun startViewUrlActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openWeiboDetailPage(weiboId: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "weiboId: $weiboId")
        }
        val weiboAppIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("sinaweibo://detail?mblogid=$weiboId"))
        weiboAppIntent.addCategory(Intent.CATEGORY_DEFAULT)
        if (weiboAppIntent.resolveActivity(packageManager) != null) {
            startActivity(weiboAppIntent)
        } else {
            startViewUrlActivity(WeiboLinkUtils.createWeiboCnUrl(weiboId))
        }
    }

    private fun openWeiboDetailPageFromUrl(url: String) {
        val weiboId = WeiboLinkUtils.getWeiboIdFromUrl(url)
        if (weiboId != null) {
            return openWeiboDetailPage(weiboId)
        }
        return startViewUrlActivity(url)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
