package io.github.wangcheng.layback

import android.util.Log
import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter

class CardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val cardView = ImageCardView(parent.context)
        cardView.cardType = ImageCardView.CARD_TYPE_FLAG_IMAGE_ONLY
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        Log.d(TAG, "onBindViewHolder")
        val cardView = viewHolder.view as ImageCardView
        cardView.setMainImageDimensions(
            (CARD_WIDTH * SCALE).toInt(),
            (CARD_HEIGHT * SCALE).toInt()
        )
        if (item is LauncherItem) {
            cardView.titleText = item.label
            val banner = item.loadBanner()
            if (item.isInput) {
                cardView.cardType = ImageCardView.CARD_TYPE_INFO_OVER
                cardView.contentText = item.description
            }
            if ((banner == null) || ((banner.intrinsicWidth.toFloat() / banner.intrinsicHeight.toFloat()) <= 1f)) {
                cardView.mainImage = IconTextBanner(item.label, banner)
            } else {
                cardView.mainImage = banner
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.mainImage = null
    }

    companion object {
        private const val TAG = "CardPresenter"

        private const val SCALE = 1F
        private const val CARD_WIDTH = 320
        private const val CARD_HEIGHT = 180
    }
}
