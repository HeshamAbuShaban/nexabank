package com.nexabank.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Flips a card view with a 3D rotation animation.
 *
 * @param view The View to be flipped (typically a CardView or similar).
 * @param isFrontVisible A flag indicating whether the front of the card is currently visible.
 * @param onFlipComplete A callback function to be executed after the flip animation is complete.
 *                       It receives a boolean indicating whether the front is now visible.
 */

fun flipCardWithAnimatorSet(view: View, isFrontVisible: Boolean, onFlipComplete: (Boolean) -> Unit) {
    val scale = view.context.resources.displayMetrics.density
    if (view.cameraDistance != 8000 * scale) {
        view.cameraDistance = 8000 * scale
    }

    val startRotation = if (isFrontVisible) 0f else -180f
    val endRotation = if (isFrontVisible) 90f else -90f
    val nextStartRotation = if (isFrontVisible) -90f else 90f
    val nextEndRotation = if (isFrontVisible) 0f else -180f

    val flipOut = ObjectAnimator.ofFloat(view, "rotationY", startRotation, endRotation).apply {
        duration = 400
        interpolator = AccelerateDecelerateInterpolator()
    }

    val flipIn = ObjectAnimator.ofFloat(view, "rotationY", nextStartRotation, nextEndRotation).apply {
        duration = 400
        interpolator = AccelerateDecelerateInterpolator()
    }

    val animatorSet = AnimatorSet().apply {
        playSequentially(flipOut, flipIn)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                // Change content here for back view
                if (isFrontVisible) {
                    // Update content to back view
                    view.rotationY = endRotation
                } else {
                    // Update content to front view
                    view.rotationY = startRotation
                    onFlipComplete(true)
                }
            }
            override fun onAnimationEnd(animation: Animator) {
                view.rotationY = nextEndRotation
                onFlipComplete(!isFrontVisible)
            }
        })
    }

    animatorSet.start()
}