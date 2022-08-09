package io.github.wangcheng.notch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS

class OverlayWindow(private val context: Context) {
    private val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager

    private val windowLayoutParams = initWindowLayoutParams()

    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("InflateParams")
    private val overlayWindowView = layoutInflater.inflate(R.layout.overlay_window, null)

    private fun initWindowLayoutParams(): WindowManager.LayoutParams {
        val bounds = windowManager.currentWindowMetrics.bounds.also {
            Log.d("Metrics", "${it.width()}x${it.height()}")
        }

        return WindowManager.LayoutParams(
            bounds.width(),
            bounds.height(),
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
            PixelFormat.TRANSLUCENT
        ).also {
            it.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
        }
    }

    fun open() {
        try {
            if (overlayWindowView.windowToken == null) {
                if (overlayWindowView.parent == null) {
                    windowManager.addView(overlayWindowView, windowLayoutParams)
                }
            }
        } catch (e: Exception) {
            Log.d("OverlayWindow", "Error: $e")
        }
    }

    fun close() {
        try {
            // remove the view from the window
            (context.getSystemService(WINDOW_SERVICE) as WindowManager).removeView(overlayWindowView)
            // invalidate the view
            overlayWindowView.invalidate()
            // remove all views
            (overlayWindowView.parent as ViewGroup).removeAllViews()

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: java.lang.Exception) {
            Log.d("Error2", e.toString())
        }
    }
}
