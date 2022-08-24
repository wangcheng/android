package io.github.wangcheng.weibolink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Intent.ACTION_VIEW) {
            val url = intent.data.toString()
            val qingxiangMatch = WeiboLinkUtils.convertWeiboQingxiang(url)
            if (qingxiangMatch != null) {
                startViewUrlActivity(qingxiangMatch)
            }
        }
        finish()
    }

    private fun startViewUrlActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
