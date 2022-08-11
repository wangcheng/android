package io.github.wangcheng.layback

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.graphics.withTranslation
import androidx.palette.graphics.Palette

class IconTextBanner(private val label: String, private val icon: Drawable?) : Drawable() {
    private val imagePaint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }
    private val textPaint = TextPaint().apply {
        textSize = TEXT_SIZE
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }

    override fun draw(canvas: Canvas) {
        val (fgColor, bgColor) = getColors(icon)
        textPaint.color = fgColor
        canvas.drawColor(bgColor)
        drawText(label, canvas)
        if (icon is BitmapDrawable) {
            drawIcon(icon, canvas)
        }
    }

    private fun getColors(icon: Drawable?): Pair<Int, Int> {
        if (icon is BitmapDrawable) {
            val palette = Palette.from(icon.bitmap).generate()
            val swatch = palette.darkVibrantSwatch
            swatch?.let {
                return Pair(Color.WHITE, swatch.rgb)
            }
        }
        return Pair(Color.GRAY, Color.WHITE)
    }

    private fun drawText(text: String, canvas: Canvas) {
        val textMarginLeft = MARGIN * 2 + ICON_SIZE
        val staticLayout = StaticLayout.Builder
            .obtain(
                text, 0, text.length, textPaint,
                (CARD_WIDTH - textMarginLeft - MARGIN).toInt(),
            )
            .build()
        val textMarginTop = (CARD_HEIGHT - staticLayout.height) / 2f
        canvas.withTranslation(textMarginLeft, textMarginTop) {
            staticLayout.draw(this)
        }
    }

    private fun drawIcon(icon: BitmapDrawable, canvas: Canvas) {
        val destRect = RectF(
            MARGIN,
            (CARD_HEIGHT - ICON_SIZE) / 2,
            MARGIN + ICON_SIZE,
            (CARD_HEIGHT + ICON_SIZE) / 2,
        )
        canvas.drawBitmap(icon.bitmap, null, destRect, imagePaint)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int =
        PixelFormat.OPAQUE

    override fun getIntrinsicWidth(): Int = CARD_WIDTH

    override fun getIntrinsicHeight(): Int = CARD_HEIGHT

    companion object {
        private const val TEXT_SIZE = 32F
        private const val ICON_SIZE = 96F
        private const val MARGIN = 16F
        private const val CARD_WIDTH = 320
        private const val CARD_HEIGHT = 180
    }
}
