package io.github.wangcheng.lockscreen;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {
    static String ACTION_LOCK_SCREEN = "io.github.wangcheng.lockscreen.LOCK_SCREEN";

    public static boolean isAccessibilityServiceEnabled(Context context, AccessibilityManager am) {
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()))
                return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (isAccessibilityServiceEnabled(context, accessibilityManager)) {
            Intent intent = new Intent(getApplicationContext(), LockScreenAccessibilityService.class);
            intent.setAction(ACTION_LOCK_SCREEN);
            startService(intent);
        } else {
            openSettings();
        }
        finish();
    }

    private void openSettings() {
        Context context = getApplicationContext();
        String serviceName = getString(R.string.accessibility_service_name);
        String toastContentTemplate = getString(R.string.accessibility_settings_toast);
        String toastContent = String.format(toastContentTemplate, serviceName);
        Toast toast = Toast.makeText(context, toastContent, Toast.LENGTH_LONG);
        toast.show();

        Intent goToSettings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        goToSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goToSettings);
    }
}
