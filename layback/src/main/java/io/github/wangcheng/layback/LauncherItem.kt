package io.github.wangcheng.layback

import android.content.Intent
import android.graphics.drawable.Drawable

/**
 * @property id
 * @property label
 * @property description
 * @property loadBanner
 * @property launchIntent
 * @property isInput
 */
data class LauncherItem(
    val id: String,
    val label: String,
    val description: String?,
    val loadBanner: () -> Drawable?,
    val launchIntent: Intent,
    val isInput: Boolean,
)
