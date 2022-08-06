package io.github.wangcheng.layback

import android.content.Intent
import android.graphics.drawable.Drawable

data class LauncherItem(
    val id: String,
    val label: String,
    val description: String?,
    val loadBanner: () -> Drawable?,
    val launchIntent: Intent,
    val isInput: Boolean,
)
