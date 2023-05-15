package io.github.wangcheng.lockscreen

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent

@Suppress("DEPRECATION")
class LockScreenAccessibilityService : AccessibilityService() {
    private fun isHapticFeedbackEnabled(): Boolean {
        val contentResolver = contentResolver
        val value = Settings.System.getInt(
            contentResolver,
            Settings.System.HAPTIC_FEEDBACK_ENABLED, 0,
        )
        return value != 0
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent) {}
    override fun onInterrupt() {}
    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int,
    ): Int {
        val action = intent.action
        if (action != null && action == MainActivity.ACTION_LOCK_SCREEN) {
            val isSuccessful = performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
            if (isSuccessful) {
                performHapticFeedbackIfEnabled()
            } else {
                Log.d("LockScreenAccessibilityService", "failed ")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun performHapticFeedbackIfEnabled() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val effect =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                VibrationEffect.createPredefined(
                    VibrationEffect.EFFECT_TICK,
                )
            } else {
                VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE)
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val attr =
                VibrationAttributes.Builder().setUsage(VibrationAttributes.USAGE_TOUCH).build()
            vibrator.vibrate(effect, attr)
            return
        }
        if (isHapticFeedbackEnabled()) {
            vibrator.vibrate(effect)
        }
    }

    companion object {
        const val VIBRATION_DURATION = 5L
    }
}
