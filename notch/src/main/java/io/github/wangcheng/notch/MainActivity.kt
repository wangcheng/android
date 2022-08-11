package io.github.wangcheng.notch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        findViewById<Button>(R.id.button).setOnLongClickListener {
            openWindow()
            return@setOnLongClickListener true
        }
        openWindow()
        updateText()
    }

    override fun onResume() {
        super.onResume()
        updateText()
    }

    private fun openWindow() {
        if (Settings.canDrawOverlays(applicationContext)) {
            val window = OverlayWindow(applicationContext)
            window.open()
        } else {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }
    }

    private fun hasPermission(): Boolean = Settings.canDrawOverlays(applicationContext)

    private fun updateText() {
        val textId =
            if (hasPermission()) R.string.text_have_permission else R.string.text_no_permission
        findViewById<TextView>(R.id.text).text = getString(textId)
        val buttonTextId =
            if (hasPermission()) R.string.button_text_have_permission else R.string.button_text_no_permission
        findViewById<Button>(R.id.button).text = getString(buttonTextId)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
