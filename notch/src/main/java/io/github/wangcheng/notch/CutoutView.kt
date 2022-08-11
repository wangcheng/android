package io.github.wangcheng.notch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowInsets

class CutoutView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var windowInsets: WindowInsets? = null
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        Log.d("CutoutView", "onDraw")
        super.onDraw(canvas)
        val displayCutout = windowInsets?.displayCutout ?: return

        Log.d("CutoutView", displayCutout.toString())

        val parentView = parent as View

        parentView.layoutParams.let {
            Log.d("Metrics", "layoutParams ${it.width}x${it.height}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            displayCutout.cutoutPath?.let {
                canvas.drawPath(it, paint)
            }
        } else {
            displayCutout.boundingRects.forEach {
                Log.d("Metrics", "Rect ${it.left},${it.top},${it.right},${it.bottom}")
                canvas.drawRect(it, paint)
            }
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        windowInsets = insets
        invalidate()
        return super.onApplyWindowInsets(insets)
    }
}
