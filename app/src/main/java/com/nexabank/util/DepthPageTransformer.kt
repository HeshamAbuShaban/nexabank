package com.nexabank.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class DepthPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        if (position < -1 || position > 1) {
            view.alpha = 0f
        } else if (position <= 0 || position <= 1) {
            view.alpha = 1f
            view.translationX = -position * view.width
            view.scaleX = 1 - abs(position)
            view.scaleY = 1 - abs(position)
        }
    }
}
