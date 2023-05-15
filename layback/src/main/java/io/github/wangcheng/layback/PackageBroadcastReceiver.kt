package io.github.wangcheng.layback

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PackageBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, intent.toString())
    }

    companion object {
        const val TAG = "PackageBroadcastReceiver"
    }
}
