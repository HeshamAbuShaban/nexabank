package com.nexabank.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

fun flipCard(view: View) {
    val scale = view.context.resources.displayMetrics.density
    view.cameraDistance = 8000 * scale

    val flipOut = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f)
    flipOut.duration = 300
    flipOut.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            // Change content here for back view
            view.rotationY = -90f
            val flipIn = ObjectAnimator.ofFloat(view, "rotationY", -90f, 0f)
            flipIn.duration = 300
            flipIn.start()
        }
    })
    flipOut.start()
}
