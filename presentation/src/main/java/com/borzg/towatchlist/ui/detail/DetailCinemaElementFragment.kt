package com.borzg.towatchlist.ui.detail

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.borzg.domain.model.common.CinemaElement
import com.borzg.towatchlist.R
import com.borzg.towatchlist.utils.hideView
import com.borzg.towatchlist.utils.showView
import com.borzg.towatchlist.utils.simpleStartAlphaAnimation
import com.borzg.towatchlist.utils.startAlphaAnimationIfHidden
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.IllegalStateException

abstract class DetailCinemaElementFragment : Fragment() {

    private val viewBindersSet = mutableSetOf<ViewBinder>()

    /**
     * Should contain the logic for binders initialization (methods addBinderForLayout)
     */
    abstract fun initializeBinders()

    /**
     * Should contain the logic for displaying layout when each part of detail information is empty
     */
    abstract fun showNothing()

    /**
     * Should contain the logic for displaying details layout
     */
    abstract fun showDetailLayout()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeBinders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearBinders()
    }

    fun FloatingActionButton.setStateDependingOnCinemaElementState(cinemaElement: CinemaElement) {
        val isElementAddedToDb = cinemaElement.isDisplayedInWatchList == true
        if (visibility == View.GONE && !isElementAddedToDb) {
            show()
            return
        }
        if (visibility == View.VISIBLE && isElementAddedToDb) {
            hideFab()
            return
        }
    }

    /**
     * Creates a ViewBinder to layout and add it to viewBindersSet
     * Should be used in initializeBinders() function
     * @param binder returns boolean defines if bind was success
     */
    fun addBinderForLayout(layout: View, binder: CinemaElement.() -> Boolean) {
        viewBindersSet.add(ViewBinder(layout, binder))
    }

    /**
     * Binds details layout in accordance with the initialized ViewBinders
     * Should be used when fragment view is created
     * @throws IllegalStateException when viewBindersSet is empty, which means that initializeBinders() were not override
     * or contains a mistake
     */
    fun bindDetailsLayout(element: CinemaElement) {
        if (viewBindersSet.isEmpty()) throw IllegalStateException("ViewBinders are not initialized. Maybe you forget to override initializeBinders()")
        viewBindersSet.forEach { viewBinder -> viewBinder.bind(element) }
        if (bindingWasFailed()) showNothing()
        else showDetailLayout()
    }

    /**
     * @see bindDetailsLayout Supports same functionality but with a simple alpha entry animation
     */
    fun bindDetailsLayoutWithAnimation(element: CinemaElement) {
        if (viewBindersSet.isEmpty()) throw IllegalStateException("ViewBinders are not initialized. Maybe you forget to override initializeBinders()")
        viewBindersSet.forEach { viewBinder -> viewBinder.bindWithAnimation(element) }
        if (bindingWasFailed()) showNothing()
        else showDetailLayout()
    }

    private fun bindingWasFailed(): Boolean {
        val successList = viewBindersSet.filter { it.isSuccess }
        return successList.isEmpty()
    }

    /**
     * Simple animation for poster
     * Combines in itself alpha and y-translation effects
     */
    fun View.animatePosterAppearance() {
        val yAnimationValue = 150f
        showView()
        y += yAnimationValue
        alpha = 0.4f
        val animation = animate().translationYBy(-yAnimationValue).alpha(1f)
        animation.duration = 600
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    /**
     * Hides fab with animation
     */
    private fun FloatingActionButton.hideFab() {
        val params = layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        hide()
    }

    private fun clearBinders() {
        viewBindersSet.clear()
    }

    @ColorInt
    private fun getColorForPerformance(performance: Float): Int {
        if (performance < 0 || performance > 10) return resources.getColor(R.color.primaryTextColor)
        if (performance < 4) return resources.getColor(R.color.awfulPerformance)
        if (performance < 6) return resources.getColor(R.color.mediumPerformance)
        if (performance < 8) return resources.getColor(R.color.goodPerformance)
        return resources.getColor(R.color.perfectPerformance)
    }

    /**
     * Sets the text color depending on the rating
     * @param rating must belong to the range of 0 to 10
     */
    fun TextView.setTextColorForRating(rating: Float) {
        this.setTextColor(getColorForPerformance(rating))
    }

    /**
     * Class containing logic of binding layouts with current cinemaElement
     * @param layout keeps layout which will hide if binding was failed
     * @param binder keeps binding logic for layout that will be define in children fragments
     */
    private data class ViewBinder(var layout: View, var binder: CinemaElement.() -> Boolean) {

        var isSuccess: Boolean = false

        fun bind(element: CinemaElement) {
            isSuccess = binder.invoke(element)
            if (isSuccess) layout.showView()
            else layout.hideView()
        }

        fun bindWithAnimation(element: CinemaElement, animationDuration: Long = 400) {
            isSuccess = binder.invoke(element)
            if (isSuccess) layout.startAlphaAnimationIfHidden(animationDuration)
            else layout.hideView()
        }

    }
}