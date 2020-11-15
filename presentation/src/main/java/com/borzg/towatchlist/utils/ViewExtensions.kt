package com.borzg.towatchlist.utils

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.view.isVisible

/**
 * Sets text and shows view if input is not empty,
 * otherwise hide view without changing text value
 */
fun TextView.setTextAndVisibility(text: String?) {
    if (text.isNullOrBlank()) this.visibility = View.GONE
    else {
        this.text = text
        showView()
    }
}

/**
 * Sets visibility to VISIBLE and show simple appearance alpha animation
 */
fun View.simpleStartAlphaAnimation(duration: Long = 1000L) {
    alpha = 0f
    showView()
    val anim = animate().alpha(1f)
    anim.duration = duration
    val interpolator = DecelerateInterpolator()
    anim.interpolator = interpolator
    anim.start()
}

/**
 * @see simpleStartAlphaAnimation do same but only iv view is now hidden
 */
fun View.startAlphaAnimationIfHidden(duration: Long = 1000L) {
    if (!isVisible) simpleStartAlphaAnimation(duration)
}

/**
 * Sets view visibility to GONE
 */
fun View.hideView() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

/**
 * Sets view visibility to VISIBLE
 */
fun View.showView() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}