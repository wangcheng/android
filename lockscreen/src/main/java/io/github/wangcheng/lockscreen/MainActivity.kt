package io.github.wangcheng.lockscreen

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        val accessibilityManager =
            context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (isAccessibilityServiceEnabled(context, accessibilityManager)) {
            val intent = Intent(applicationContext, LockScreenAccessibilityService::class.java).apply {
                action = ACTION_LOCK_SCREEN
            }
            startService(intent)
        } else {
            openSettings()
        }
        finish()
    }

    private fun openSettings() {
        val context = applicationContext
        val serviceName = getString(R.string.accessibility_service_name)
        val toastContentTemplate = getString(R.string.accessibility_settings_toast)
        val toastContent = String.format(toastContentTemplate, serviceName)
        val toast = Toast.makeText(context, toastContent, Toast.LENGTH_LONG)
        toast.show()
        val goToSettings = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        goToSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(goToSettings)
    }

    companion object {
        const val ACTION_LOCK_SCREEN = "io.github.wangcheng.lockscreen.LOCK_SCREEN"
        fun isAccessibilityServiceEnabled(context: Context, am: AccessibilityManager): Boolean {
            val enabledServices =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            for (enabledService in enabledServices) {
                val enabledServiceInfo = enabledService.resolveInfo.serviceInfo
                if (enabledServiceInfo.packageName == context.packageName) {
                    return true
                }
            }
            return false
        }
    }
}
