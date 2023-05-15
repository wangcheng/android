package io.github.wangcheng.lockscreen;

import android.accessibilityservice.AccessibilityService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class LockScreenAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null && action.equals(MainActivity.ACTION_LOCK_SCREEN)) {
            boolean isSuccessful = performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
            if (isSuccessful) {
                performHapticFeedbackIfEnabled();
            } else {
                Log.d("LockScreenAccessibilityService", "failed ");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean isHapticFeedbackEnabled() {
        ContentResolver contentResolver = getContentResolver();

        int value = Settings.System.getInt(contentResolver,
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 0);
        
        return value != 0;
    }

    private void performHapticFeedbackIfEnabled() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator == null) return;

        VibrationEffect effect = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                ? VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                : VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE);

/*
 TODO: use VibrationAttributes.USAGE_TOUCH in API 33 to replace isHapticFeedbackEnabled.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            VibrationAttributes attr = new VibrationAttributes.Builder().setUsage(VibrationAttributes.USAGE_TOUCH).build();
            vibrator.vibrate(effect, attr);
            return;
        }
*/

        if (isHapticFeedbackEnabled()) {
            vibrator.vibrate(effect);
        }
    }
}
